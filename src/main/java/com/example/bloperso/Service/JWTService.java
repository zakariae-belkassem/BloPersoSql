package com.example.bloperso.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTService {
    public String generateToken(UserDetails userDetails){
        return Jwts.builder().setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*24))
                .signWith(getSigninKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    private Key getSigninKey()  {

        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        return Keys.hmacShaKeyFor(key.getEncoded());
    }
    private <T> T extractClaims(String token , Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSigninKey()).build().parseClaimsJws(token).getBody();
    }
    public String extractUsername(String token) {
        return extractClaims(token, Claims::getSubject);
    }
        public Boolean isTokenValid (String token, UserDetails userDetails) {
        final String username = extractUsername(token);
                return (username.equals(userDetails.getUsername()) && !isTokenExpired(token) );
        }

    private boolean isTokenExpired(String token) {
        return extractClaims(token , Claims :: getExpiration).before(new Date());
    }

}
