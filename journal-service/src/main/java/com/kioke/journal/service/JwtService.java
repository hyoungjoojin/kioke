package com.kioke.journal.service;

import com.kioke.journal.exception.security.ExpiredTokenException;
import com.kioke.journal.exception.security.InvalidTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;
import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
  @Autowired @Lazy private ResourceLoader resourceLoader;

  public String extractSubject(String token) throws InvalidTokenException, ExpiredTokenException {
    Claims claims;

    try {
      claims = extractClaims(token);
    } catch (IllegalArgumentException | JwtException e) {
      throw new InvalidTokenException();
    }

    if (claims.getExpiration().before(new Date())) {
      throw new ExpiredTokenException();
    }

    return claims.getSubject();
  }

  private Claims extractClaims(String token) {
    Claims claims =
        Jwts.parser().verifyWith(getPublicKey()).build().parseSignedClaims(token).getPayload();

    return claims;
  }

  private PublicKey getPublicKey() {
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

    try {
      byte[] keyBytes = Decoders.BASE64.decode(key);
      KeyFactory keyFactory = KeyFactory.getInstance("EC");
      X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);

      return keyFactory.generatePublic(keySpec);
    } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
      throw new IllegalStateException("This should not happen. " + e.toString());
    }
  }
}
