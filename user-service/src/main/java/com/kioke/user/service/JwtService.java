package com.kioke.user.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class JwtService {
  @Value(value = "${security.jwt.key}")
  private String key;

  @Value(value = "${security.jwt.expiration}")
  private Long expiration;

  public boolean isValidToken(String token, UserDetails userDetails) {
    final String username = extractClaim(token, Claims::getSubject);
    boolean isTokenExpired = extractClaim(token, Claims::getExpiration).before(new Date());

    return username.equals(userDetails.getUsername()) && !isTokenExpired;
  }

  public String extractSubject(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    Claims claims =
        Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();

    return claimsResolver.apply(claims);
  }

  public String buildToken(UserDetails userDetails) {
    return buildToken(userDetails, new HashMap<>());
  }

  public String buildToken(UserDetails userDetails, Map<String, Object> extraClaims) {
    Long currentTime = System.currentTimeMillis();

    log.info(key);
    log.info(expiration.toString());

    return Jwts.builder()
        .setClaims(extraClaims)
        .setSubject(userDetails.getUsername())
        .setIssuedAt(new Date(currentTime))
        .setExpiration(new Date(currentTime + expiration))
        .signWith(getSigningKey(), SignatureAlgorithm.HS256)
        .compact();
  }

  private Key getSigningKey() {
    byte[] keyBytes = Decoders.BASE64.decode(key);
    return Keys.hmacShaKeyFor(keyBytes);
  }
}
