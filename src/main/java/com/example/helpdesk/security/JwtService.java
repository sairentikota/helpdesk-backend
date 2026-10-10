package com.example.helpdesk.security;
import com.example.helpdesk.model.AppUser;import io.jsonwebtoken.*;import io.jsonwebtoken.security.Keys;import javax.crypto.SecretKey;import org.springframework.beans.factory.annotation.Value;import org.springframework.stereotype.Service;import java.nio.charset.StandardCharsets;import java.util.Date;
@Service public class JwtService {
 private final SecretKey key;private final long expiry;
 public JwtService(@Value("${app.jwt.secret}") String s,@Value("${app.jwt.expiration-ms}") long e){if(s.getBytes(StandardCharsets.UTF_8).length<32)throw new IllegalArgumentException("JWT_SECRET must be at least 32 bytes");key=Keys.hmacShaKeyFor(s.getBytes(StandardCharsets.UTF_8));expiry=e;}
 public String generate(AppUser u){return Jwts.builder().subject(u.getEmail()).claim("role",u.getRole().name()).issuedAt(new Date()).expiration(new Date(System.currentTimeMillis()+expiry)).signWith(key).compact();}
 public String subject(String t){return Jwts.parser().verifyWith(key).build().parseSignedClaims(t).getPayload().getSubject();}
}
