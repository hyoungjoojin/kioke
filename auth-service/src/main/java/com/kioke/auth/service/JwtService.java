package com.kioke.auth.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Date;
import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
  @Autowired @Lazy private ResourceLoader resourceLoader;

  @Value(value = "${security.jwt.expiration}")
  private Long expiration;

  public String buildToken(String uid) {
    Long currentTime = System.currentTimeMillis();

    return Jwts.builder()
        .setSubject(uid)
        .setIssuedAt(new Date(currentTime))
        .setExpiration(new Date(currentTime + expiration))
        .signWith(getSigningKey(), SignatureAlgorithm.ES256)
        .compact();
  }

  private PrivateKey getSigningKey() {
    Resource resource = resourceLoader.getResource("classpath:/certs/private.pem");

    String key = "";

    try {
      InputStream inputStream = resource.getInputStream();
      key = new String(inputStream.readAllBytes(), Charset.defaultCharset());

    } catch (Exception e) {
      throw new RuntimeException(
          "Error while reading resource /certs/private.pem. " + e.toString());
    }

    key =
        key.replace("-----BEGIN PRIVATE KEY-----", "")
            .replaceAll(System.lineSeparator(), "")
            .replace("-----END PRIVATE KEY-----", "");

    try {
      byte[] keyBytes = Decoders.BASE64.decode(key);

      KeyFactory keyFactory = KeyFactory.getInstance("EC");
      PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);

      return (PrivateKey) keyFactory.generatePrivate(keySpec);

    } catch (Exception e) {
      throw new RuntimeException("Error while processing key contents. " + e.toString());
    }
  }
}
