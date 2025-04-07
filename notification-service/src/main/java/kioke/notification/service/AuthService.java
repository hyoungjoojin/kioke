package kioke.notification.service;

import io.jsonwebtoken.io.Decoders;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import kioke.commons.service.AbstractAuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthService extends AbstractAuthService {
  @Autowired private ResourceLoader resourceLoader;

  @Override
  protected PublicKey getPublicKey() {
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
