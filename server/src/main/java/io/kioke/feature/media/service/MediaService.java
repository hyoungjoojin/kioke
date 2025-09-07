package io.kioke.feature.media.service;

import io.kioke.feature.media.domain.Media;
import io.kioke.feature.media.repository.MediaRepository;
import java.io.IOException;
import java.time.Duration;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

@Service
public class MediaService {

  private final MediaRepository mediaRepository;

  private final S3Client s3Client;
  private final S3Presigner s3Presigner;

  @Value("${s3.bucket}")
  private String bucket;

  public MediaService(MediaRepository mediaRepository, S3Client s3Client, S3Presigner s3Presigner) {
    this.mediaRepository = mediaRepository;
    this.s3Client = s3Client;
    this.s3Presigner = s3Presigner;
  }

  @Transactional(rollbackFor = Exception.class)
  public String uploadFile(MultipartFile file) throws IOException {
    Media media = Media.of(UUID.randomUUID().toString());
    media = mediaRepository.save(media);

    PutObjectRequest request =
        PutObjectRequest.builder()
            .bucket(bucket)
            .key(media.key())
            .contentType(file.getContentType())
            .build();

    s3Client.putObject(request, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

    return media.id();
  }

  @Transactional(readOnly = true)
  public String getPresignedUrl(String mediaId) {
    Media media = mediaRepository.findById(mediaId).orElseThrow();

    GetObjectRequest objectRequest =
        GetObjectRequest.builder().bucket(bucket).key(media.key()).build();

    GetObjectPresignRequest presignRequest =
        GetObjectPresignRequest.builder()
            .signatureDuration(Duration.ofMinutes(10))
            .getObjectRequest(objectRequest)
            .build();

    PresignedGetObjectRequest presignedRequest = s3Presigner.presignGetObject(presignRequest);
    return presignedRequest.url().toExternalForm();
  }
}
