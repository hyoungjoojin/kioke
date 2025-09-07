package io.kioke.config;

import java.net.URI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

@Profile("!test")
@Configuration
public class S3Config {

  @Value("${s3.endpoint}")
  private String endpoint;

  @Value("${s3.access-key}")
  private String accessKey;

  @Value("${s3.secret-key}")
  private String secretKey;

  @Bean
  public S3Client s3Client() {
    return S3Client.builder()
        .region(Region.of("auto"))
        .endpointOverride(URI.create(endpoint))
        .credentialsProvider(credentialsProvider())
        .build();
  }

  @Bean
  public S3Presigner s3Presigner() {
    return S3Presigner.builder()
        .region(Region.of("auto"))
        .endpointOverride(URI.create(endpoint))
        .credentialsProvider(credentialsProvider())
        .serviceConfiguration(S3Configuration.builder().pathStyleAccessEnabled(false).build())
        .build();
  }

  private StaticCredentialsProvider credentialsProvider() {
    AwsCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);
    return StaticCredentialsProvider.create(credentials);
  }
}
