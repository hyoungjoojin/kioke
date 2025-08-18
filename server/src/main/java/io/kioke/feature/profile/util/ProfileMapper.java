package io.kioke.feature.profile.util;

import io.kioke.feature.profile.dto.ProfileDto;
import io.kioke.feature.profile.dto.response.GetMyProfileResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProfileMapper {

  public GetMyProfileResponseDto toGetMyProfileResponse(ProfileDto profile);
}
