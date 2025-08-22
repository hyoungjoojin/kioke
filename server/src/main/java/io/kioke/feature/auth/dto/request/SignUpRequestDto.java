package io.kioke.feature.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record SignUpRequestDto(
    @NotNull @Email String email, @NotNull @Length(min = 8) String password) {}
