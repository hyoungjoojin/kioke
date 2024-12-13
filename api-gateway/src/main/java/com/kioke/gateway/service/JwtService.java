package com.kioke.gateway.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;
import java.util.function.Function;
import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
  @Autowired @Lazy private ResourceLoader resourceLoader;

  public boolean isValidToken(String token) {
    try {
      boolean isTokenExpired = extractClaim(token, Claims::getExpiration).before(new Date());
      return !isTokenExpired;

    } catch (Exception e) {
      return false;
    }
  }

  public String extractSubject(String token) throws Exception {
    return extractClaim(token, Claims::getSubject);
  }

  private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) throws Exception {
    Claims claims =
        Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();

    return claimsResolver.apply(claims);
  }

  private PublicKey getSigningKey() throws Exception {
    Resource resource = resourceLoader.getResource("classpath:/certs/public.pem");

    String key = "";

    try {
      InputStream inputStream = resource.getInputStream();
      key = new String(inputStream.readAllBytes(), Charset.defaultCharset());

    } catch (Exception e) {
      throw new RuntimeException("Error while reading resource /certs/public.pem. " + e.toString());
    }

    key =
        key.replace("-----BEGIN PUBLIC KEY-----", "")
            .replaceAll(System.lineSeparator(), "")
            .replace("-----END PUBLIC KEY-----", "");

    byte[] keyBytes = Decoders.BASE64.decode(key);

    KeyFactory keyFactory = KeyFactory.getInstance("EC");
    X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);

    return keyFactory.generatePublic(keySpec);
  }
}
