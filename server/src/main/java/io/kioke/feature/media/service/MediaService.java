package io.kioke.feature.media.service;

import io.kioke.feature.media.domain.Media;
import io.kioke.feature.media.dto.response.MediaResponse;
import io.kioke.feature.media.repository.MediaRepository;
import io.kioke.feature.media.util.MediaMapper;
import java.time.Duration;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;

@Service
public class MediaService {

  private final MediaRepository mediaRepository;
  private final MediaMapper mediaMapper;

  private final S3Client s3Client;
  private final S3Presigner s3Presigner;

  @Value("${s3.bucket}")
  private String bucket;

  public MediaService(
      MediaRepository mediaRepository,
      MediaMapper mediaMapper,
      S3Client s3Client,
      S3Presigner s3Presigner) {
    this.mediaRepository = mediaRepository;
    this.mediaMapper = mediaMapper;
    this.s3Client = s3Client;
    this.s3Presigner = s3Presigner;
  }

  @Transactional(readOnly = true)
  public List<MediaResponse> getMedia(List<? extends Media> mediaList) {
    List<MediaResponse> presignedRequests =
        mediaList.stream()
            .map(
                media -> {
                  GetObjectRequest objectRequest =
                      GetObjectRequest.builder().bucket(bucket).key(media.getKey()).build();

                  GetObjectPresignRequest presignRequest =
                      GetObjectPresignRequest.builder()
                          .signatureDuration(Duration.ofMinutes(10))
                          .getObjectRequest(objectRequest)
                          .build();

                  PresignedGetObjectRequest presignedRequest =
                      s3Presigner.presignGetObject(presignRequest);

                  return mediaMapper.map(media.getId(), presignedRequest);
                })
            .toList();

    return presignedRequests;
  }

  public MediaResponse uploadMedia(Media media) {
    PutObjectRequest objectRequest =
        PutObjectRequest.builder().bucket(bucket).key(media.getKey()).build();

    PresignedPutObjectRequest presignRequest =
        s3Presigner.presignPutObject(
            builder ->
                builder.putObjectRequest(objectRequest).signatureDuration(Duration.ofMinutes(10)));

    return mediaMapper.map(media.getId(), presignRequest);
  }

  @Transactional(rollbackFor = Exception.class)
  public void deleteMedia(Media media) {
    DeleteObjectRequest deleteObjectRequest =
        DeleteObjectRequest.builder().bucket(bucket).key(media.getKey()).build();
    s3Client.deleteObject(deleteObjectRequest);

    mediaRepository.delete(media);
  }
}
