package io.github.mqdev.apicursos.modules.user.useCases;

import org.springframework.stereotype.Service;

import io.github.mqdev.apicursos.modules.user.dto.AuthUserResponseDTO;
import io.github.mqdev.apicursos.modules.user.repositories.UserRepository;

import java.time.Instant;
import java.time.Duration;
import java.util.Arrays;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Value;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

@Service
public class AuthUserUseCase {

    @Value("${security.jwt.secret}")
    private String secret;

    @Value("${security.jwt.issuer}")
    private String issuer;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthUserResponseDTO execute(String username, String password) throws AuthenticationException {
        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        var passwordMatch = passwordEncoder.matches(password, user.getPassword());

        if (!passwordMatch) {
            throw new AuthenticationException("Invalid password");
        }

        var roles = Arrays.asList("USER");

        var expiration = Instant.now().plus(Duration.ofHours(2));

        Algorithm algorithm = Algorithm.HMAC256(secret);
        var token = JWT.create()
                .withIssuer(issuer)
                .withSubject(user.getId().toString())
                .withExpiresAt(expiration)
                .withClaim("roles", roles)
                .sign(algorithm);

        return AuthUserResponseDTO.builder()
                .access_token(token)
                .expires_in(expiration.toEpochMilli())
                .roles(roles)
                .build();

    }
    
}
