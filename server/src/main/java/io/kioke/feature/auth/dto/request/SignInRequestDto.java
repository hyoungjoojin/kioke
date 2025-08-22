package io.kioke.feature.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record SignInRequestDto(@NotNull @Email String email, @NotNull String password) {}
