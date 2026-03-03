package com.promptcourse.service;

import com.promptcourse.dto.AuthResponse;
import com.promptcourse.dto.LoginRequest;
import com.promptcourse.dto.RegisterRequest;
import com.promptcourse.entity.RefreshToken;
import com.promptcourse.entity.User;
import com.promptcourse.exception.ApiException;
import com.promptcourse.repository.RefreshTokenRepository;
import com.promptcourse.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new ApiException("Username already exists", HttpStatus.CONFLICT);
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ApiException("Email already exists", HttpStatus.CONFLICT);
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user = userRepository.save(user);

        String accessToken = jwtService.generateAccessToken(user.getId(), user.getUsername());
        String refreshToken = createRefreshToken(user);

        return new AuthResponse(accessToken, refreshToken, "Bearer", user.getId(), user.getUsername());
    }

    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new ApiException("Invalid username or password", HttpStatus.UNAUTHORIZED));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new ApiException("Invalid username or password", HttpStatus.UNAUTHORIZED);
        }

        String accessToken = jwtService.generateAccessToken(user.getId(), user.getUsername());
        String refreshToken = createRefreshToken(user);

        return new AuthResponse(accessToken, refreshToken, "Bearer", user.getId(), user.getUsername());
    }

    public AuthResponse refreshToken(String refreshTokenString) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(refreshTokenString)
                .orElseThrow(() -> new ApiException("Invalid refresh token", HttpStatus.UNAUTHORIZED));

        if (refreshToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            refreshTokenRepository.delete(refreshToken);
            throw new ApiException("Refresh token expired", HttpStatus.UNAUTHORIZED);
        }

        User user = refreshToken.getUser();
        String newAccessToken = jwtService.generateAccessToken(user.getId(), user.getUsername());
        String newRefreshToken = createRefreshToken(user);

        // Delete old refresh token
        refreshTokenRepository.delete(refreshToken);

        return new AuthResponse(newAccessToken, newRefreshToken, "Bearer", user.getId(), user.getUsername());
    }

    @Transactional
    public void logout(String refreshTokenString) {
        refreshTokenRepository.findByToken(refreshTokenString)
                .ifPresent(refreshTokenRepository::delete);
    }

    private String createRefreshToken(User user) {
        // Delete old refresh tokens for this user
        refreshTokenRepository.deleteByUser(user);

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiryDate(LocalDateTime.now().plusDays(1));
        refreshTokenRepository.save(refreshToken);

        return refreshToken.getToken();
    }
}
