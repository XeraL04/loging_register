package com.login.login.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {

    private String secretKey = "SecretKeyOfMine";

    //generate the JWT token
    public String generateToken(String username){
        return JWT.create()
                .withSubject(username)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() +1000 * 60 * 60))
                .sign(Algorithm.HMAC256(secretKey));
    }

    //parse the token
    private DecodedJWT parseToken(String token){
        return JWT.require(Algorithm.HMAC256(secretKey)).build().verify(token);
    }

    //extract username from the token
    public String extractUsername(String token){
        return parseToken(token).getSubject();
    }

    //check token if expired
    public boolean isTokenExpired(String token){
        return parseToken(token).getExpiresAt().before(new Date());
    }

    //validate token
    public boolean validateToken(String token, String username){
        return (username.equals(extractUsername(token)) && !isTokenExpired(token));
    }

}
