package com.stayFinder.proyectoFinal.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.stayFinder.proyectoFinal.entity.enums.Role;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Component
public class JWTUtil { // tiene los metodos para verificar la validez del jwt 
    @Value("${jwt.key}")
    private String jwtKey;
    @Value("${jwt.tokenExpiration}")
    private int tokenExpiration;

    /*@Value("${jwt.refreshTokenExpiration}")
    private int refreshTokenExpiration;
   */
   private SecretKey secretKey;

    @PostConstruct
    public void init(){
        secretKey = Keys.hmacShaKeyFor(jwtKey.getBytes(StandardCharsets.UTF_8));
    }

    public String GenerateToken(long id, String email, Role role){
        long expirationInMs = tokenExpiration;
        Claims claims = Jwts.claims()
                .setId(Long.toString(id))
                .setSubject(email)
                .setIssuer(role.toString())
                .setExpiration(new Date(System.currentTimeMillis() + expirationInMs))
                .setIssuedAt(Date.from(Instant.now()));

     
       
        return Jwts.builder()
                .setClaims(claims)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String GetEmailFromToken(String token, boolean refresh){
        return Jwts.parserBuilder()
                .setSigningKey(secretKey).build()
                .parseClaimsJwt(token)
                .getBody()
                .getSubject();
    }

    public UUID GetSessionIdFromRefreshToken(String token){
        String sessionId =Jwts.parserBuilder()
                .setSigningKey(secretKey).build()
                .parseClaimsJws(token)
                .getBody()
                .get("sessionId", String.class);

        return UUID.fromString(sessionId);
    }


    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    private Boolean isTokenExpired(String token) {
        Claims claims = extractAllClaims(token);
        return claims.getExpiration().before(new Date());
    }

    public boolean ValidateRecoverToken(String token, String userEmail){
        try {
            final String username = extractUsername(token);
            return (username.equals(userEmail) && !isTokenExpired(token));
        } catch (SecurityException e) {
            System.out.println("Invalid JWT signature: " + e.getMessage());
        } catch (MalformedJwtException e) {
            System.out.println("Invalid JWT token: " + e.getMessage());
        } catch (ExpiredJwtException e) {
            System.out.println("JWT token is expired: " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            System.out.println("JWT token is unsupported: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("JWT claims string is empty: " + e.getMessage());
        }
        return false;
    }


    public boolean ValidateJwtToken(String token, UserDetails userDetails) {
        try {
            final String username = extractUsername(token);
            return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
        } catch (SecurityException e) {
            System.out.println("Invalid JWT signature: " + e.getMessage());
        } catch (MalformedJwtException e) {
            System.out.println("Invalid JWT token: " + e.getMessage());
        } catch (ExpiredJwtException e) {
            System.out.println("JWT token is expired: " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            System.out.println("JWT token is unsupported: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("La cadena de claims del JWT está vacía: " + e.getMessage());
        }
        return false;
    }
}