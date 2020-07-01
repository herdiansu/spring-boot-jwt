package com.herdian.springsecurityjwt.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtil {

    private String SECRET_KEY = "secret";
    // get token dan return username
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }
   // get token dan return expiration Date
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    // method untuk get data user (claims)
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }
    // get token dan return expiration (true/false) sebelum current Date
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    // generate token dari userDetails (username & password)
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername()); // call createToken method
    }
    // createToken method mapping the claims object for create JWT token
    private String createToken(Map<String, Object> claims, String subject) {
        // Jwt configuration to set claims object, set expiration date based on current time
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // konfigurasi token expiration date
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }
    // validate token username dari userDetails
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}