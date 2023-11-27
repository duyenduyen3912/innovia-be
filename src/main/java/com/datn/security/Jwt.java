package com.datn.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;

public class Jwt {

	private static final String SECRET_KEY = "y3vCHusKutukl6frggZ7lAUuTpN6Ls4fVIGwzTaeJBM=";
    private static final long EXPIRATION_TIME = 60 * 60 * 1000; // 30 minutes in milliseconds

    private static String generateSecretKey() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] key = new byte[32];
        secureRandom.nextBytes(key);
        return Base64.getEncoder().encodeToString(key);
    }

    public  String generateJwtToken(String username) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + EXPIRATION_TIME);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }
    
    public  Claims decodeJwtToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    public  boolean isValidJwt(String authorizationHeader) {
        try {
            String token = authorizationHeader.replace("Bearer ", "");
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();

     
            Date expiration = claims.getExpiration();
            Date now = new Date();

            return expiration.after(now);
        } catch (Exception e) {
 
            return false;
        }
    }
    

//    public static void main(String[] args) {
//       String key = generateSecretKey();
//       System.out.println(key);
//    }
}
