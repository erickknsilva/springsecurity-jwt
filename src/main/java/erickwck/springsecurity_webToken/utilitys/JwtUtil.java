package erickwck.springsecurity_webToken.utilitys;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    @Value(value = "${key.jwt.secret}")
    private String secret;
    private byte[] keyBytes = Decoders.BASE64.decode(secret);
    private Key key = Keys.hmacShaKeyFor(keyBytes);

    public String generateToken(UserDetails userDetails) {


        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claim("authorities", userDetails.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(key)
                .compact();
    }


    public String getUsernameFromToken(String token) {
        SecretKey secretKey = Keys.hmacShaKeyFor(keyBytes);

        Jwt<?,?> jwt = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parse(token);

        Claims claims = (Claims) jwt.getPayload();

        return claims.getSubject();
    }

    public boolean validateToken(String token, UserDetails userDetails){
        return getUsernameFromToken(token).equalsIgnoreCase(userDetails.getUsername());
    }

}
