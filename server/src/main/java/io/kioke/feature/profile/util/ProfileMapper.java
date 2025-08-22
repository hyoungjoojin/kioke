package io.kioke.feature.profile.util;

import io.kioke.feature.profile.domain.Profile;
import io.kioke.feature.profile.dto.ProfileDto;
import io.kioke.feature.profile.dto.response.GetProfileResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProfileMapper {

  public ProfileDto toDto(Profile profile, String email);

  public GetProfileResponseDto toGetProfileResponse(ProfileDto profile);
}
