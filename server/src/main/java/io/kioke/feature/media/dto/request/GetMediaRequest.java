package io.kioke.feature.media.dto.request;

import io.kioke.common.auth.PermissionEvaluatorType;
import io.kioke.common.auth.PermissionObject;
import io.kioke.feature.media.domain.MediaContext;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record GetMediaRequest(
    @NotNull MediaContext context, @NotNull String contextId, @NotNull List<String> ids)
    implements PermissionObject {

  @Override
  public PermissionEvaluatorType type() {
    return PermissionEvaluatorType.MEDIA;
  }
}
