package org.rizki.mufrizal.jwt.configuration;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Calendar;

@Component
@Slf4j
public class JwtUtils {

    @Autowired
    private Environment environment;

    public String generateJwtToken(Authentication authentication) {
        Algorithm algorithm = Algorithm.HMAC256(environment.getRequiredProperty("jwt.secret"));
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, Integer.parseInt(environment.getRequiredProperty("jwt.time.expired")));
        return JWT.create()
                .withSubject(authentication.getName())
                .withIssuer(environment.getRequiredProperty("jwt.issuer"))
                .withExpiresAt(calendar.getTime())
                .sign(algorithm);
    }

    public Boolean validateJwtToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(environment.getRequiredProperty("jwt.secret"));
        try {
            JWT.require(algorithm)
                    .withIssuer(environment.getRequiredProperty("jwt.issuer"))
                    .acceptExpiresAt(Integer.parseInt(environment.getRequiredProperty("jwt.time.accept")))
                    .build()
                    .verify(token);
        } catch (Exception e) {
            StringWriter stringWriter = new StringWriter();
            log.error("Exception {}", e.getMessage());
            e.printStackTrace(new PrintWriter(stringWriter));
            log.error("Exception {}", stringWriter);
            return false;
        }
        return true;
    }

    public String getUserNameFromJwtToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(environment.getRequiredProperty("jwt.secret"));
        return JWT.require(algorithm).build().verify(token).getSubject();
    }
}