package io.kioke.feature.image.dto.request;

import jakarta.validation.constraints.NotNull;
import org.springframework.http.MediaType;

public record UploadImageRequest(
    @NotNull String name,
    @NotNull MediaType contentType,
    @NotNull Long contentLength,
    @NotNull Long width,
    @NotNull Long height) {}
