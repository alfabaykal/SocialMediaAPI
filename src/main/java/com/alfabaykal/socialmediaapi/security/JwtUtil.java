package com.alfabaykal.socialmediaapi.security;

import com.alfabaykal.socialmediaapi.dto.UserDto;
import com.alfabaykal.socialmediaapi.exception.UserNotFoundException;
import com.alfabaykal.socialmediaapi.service.UserService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Optional;

@Component
public class JwtUtil {

    private final UserService userService;

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration.minutes}")
    private Long expirationTime;

    public JwtUtil(UserService userService) {
        this.userService = userService;
    }

    public String generateToken(Long id, String username) {
        Date expirationDate = Date.from(ZonedDateTime.now().plusMinutes(expirationTime).toInstant());

        return JWT.create()
                .withSubject("User details")
                .withClaim("id", id)
                .withClaim("username", username)
                .withIssuedAt(new Date())
                .withIssuer("Social Media API")
                .withExpiresAt(expirationDate)
                .sign(Algorithm.HMAC256(secret));
    }

    public JwtPayload validateTokenAndRetrieveClaims(String token) throws JWTVerificationException {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                .withSubject("User details")
                .withIssuer("Social Media API")
                .withClaimPresence("id")
                .withClaimPresence("username")
                .build();

        DecodedJWT jwt = verifier.verify(token);
        return new JwtPayload(jwt.getClaim("id").asLong(),
                jwt.getClaim("username").asString());
    }

    public Long getUserIdByJwtHeader(String authHeader) throws JWTVerificationException {
        JwtPayload jwtPayload = validateTokenAndRetrieveClaims(authHeader.substring(7));
        Optional<UserDto> userDtoOptional = userService.getUserById(jwtPayload.id);

        if (userDtoOptional.isPresent()) {
            return userDtoOptional.get().getId();
        } else {
            throw new UserNotFoundException(jwtPayload.id);
        }
    }

}
