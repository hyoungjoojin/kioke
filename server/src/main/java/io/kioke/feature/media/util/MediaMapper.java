package io.kioke.feature.media.util;

import io.kioke.feature.media.dto.response.MediaResponse;
import org.mapstruct.Mapper;
import software.amazon.awssdk.awscore.presigner.PresignedRequest;

@Mapper(componentModel = "spring")
public interface MediaMapper {

  public default <T extends PresignedRequest> MediaResponse map(String mediaId, T presignRequest) {
    String url = presignRequest.url().toExternalForm();
    return new MediaResponse(mediaId, url);
  }
}
