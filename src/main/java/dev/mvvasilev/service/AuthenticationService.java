package dev.mvvasilev.service;

import dev.mvvasilev.exception.*;
import dev.mvvasilev.model.dto.AccessTokenDTO;
import dev.mvvasilev.model.dto.UserRegistrationDTO;
import dev.mvvasilev.model.entity.User;
import dev.mvvasilev.model.entity.UserRefreshToken;
import dev.mvvasilev.repository.UserRefreshTokenRepository;
import dev.mvvasilev.repository.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Component
public class AuthenticationService {

    public static final int REFRESH_TOKEN_LENGTH = 64;

    public static final int REGISTRATION_CODE_LENGTH = 70;

    public static final int ACCESS_TOKEN_VALID_TIME_IN_MINUTES = 20;

    public static final int REFRESH_TOKEN_VALID_TIME_IN_MINUTES = 10080; // 1 week

    private final UserRepository userRepository;

    private final UserRefreshTokenRepository userRefreshTokenRepository;

    private final UserService userService;

    private final JwtEncoder jwtEncoder;

    private final ModelMapper modelMapper;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationService(UserRepository userRepository, UserRefreshTokenRepository userRefreshTokenRepository, UserService userService, JwtEncoder jwtEncoder, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userRefreshTokenRepository = userRefreshTokenRepository;
        this.userService = userService;
        this.jwtEncoder = jwtEncoder;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public AccessTokenDTO login(String usernamePswHash) {
        Optional<User> user = userRepository.findByPasswordHash(usernamePswHash);

        if (user.isEmpty()) {
            throw new UnknownUserException();
        }

        if (!user.get().isRegistrationConfirmed()) {
            throw new UnconfirmedRegistrationException();
        }

        return issueAccessTokenForUser(user.get());
    }

    public boolean register(UserRegistrationDTO dto) {
        User user = modelMapper.map(dto, User.class);

        user.setPasswordHash(passwordEncoder.encode(String.format("%s;%s", dto.getUsername(), dto.getPassword())));
        user.setRegistrationCode(RandomStringUtils.randomAlphanumeric(REGISTRATION_CODE_LENGTH));

        userRepository.save(user);

        userService.sendRegistrationConfirmationEmail(user);

        return true;
    }

    public boolean confirmRegistration(String registrationCode) {
        Optional<User> user = userRepository.findByRegistrationCode(registrationCode);

        if (user.isEmpty()) {
            throw new UnknownUserException();
        }

        if (user.get().isRegistrationConfirmed()) {
            throw new RegistrationAlreadyConfirmedException();
        }

        user.get().setRegistrationConfirmed(true);

        userRepository.save(user.get());

        return true;
    }

    public AccessTokenDTO refresh(String refreshToken) {
        Optional<User> user = userRepository.findByRefreshToken(refreshToken);

        if (user.isEmpty()) {
            throw new UnknownRefreshTokenException();
        }

        if (!user.get().isRegistrationConfirmed()) {
            throw new UnconfirmedRegistrationException();
        }

        Optional<UserRefreshToken> latestToken = userRefreshTokenRepository.findLatestByUserId(user.get().getId());

        if (latestToken.isEmpty()) {
            throw new UnknownRefreshTokenException();
        }

        if (!latestToken.get().getRefreshToken().equals(refreshToken) || latestToken.get().getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new ExpiredRefreshTokenException();
        }

        return issueAccessTokenForUser(user.get());
    }

    public AccessTokenDTO issueAccessTokenForUser(User user) {
        var dto = new AccessTokenDTO();
        dto.setAccessTokenIssuedOn(LocalDateTime.now());
        dto.setRefreshTokenIssuedOn(LocalDateTime.now());
        dto.setAccessTokenExpiresAt(dto.getAccessTokenIssuedOn().plus(ACCESS_TOKEN_VALID_TIME_IN_MINUTES, ChronoUnit.MINUTES));
        dto.setRefreshTokenExpiresAt(dto.getRefreshTokenIssuedOn().plus(REFRESH_TOKEN_VALID_TIME_IN_MINUTES, ChronoUnit.MINUTES));


        var accessToken = jwtEncoder.encode(JwtEncoderParameters.from(
                JwtClaimsSet.builder()
                        .issuedAt(dto.getAccessTokenIssuedOn().toInstant(ZoneOffset.UTC))
                        .expiresAt(dto.getAccessTokenExpiresAt().toInstant(ZoneOffset.UTC))
                        .subject(Long.toString(user.getId()))
                        .claim("scp", List.of("SELF", "ALL"))
                        .build())
        ).getTokenValue();

        var refreshToken = new UserRefreshToken();

        refreshToken.setRefreshToken(RandomStringUtils.randomAlphanumeric(REFRESH_TOKEN_LENGTH));
        refreshToken.setIssuedOn(dto.getRefreshTokenIssuedOn());
        refreshToken.setExpiresAt(dto.getRefreshTokenExpiresAt());
        refreshToken.setUserId(user.getId());

        userRefreshTokenRepository.save(refreshToken);

        dto.setRefreshToken(refreshToken.getRefreshToken());
        dto.setAccessToken(accessToken);

        return dto;
    }
}
