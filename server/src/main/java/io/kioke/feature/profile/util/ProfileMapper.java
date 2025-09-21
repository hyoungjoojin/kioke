package io.kioke.feature.profile.util;

import io.kioke.feature.profile.dto.projection.MyProfileProjection;
import io.kioke.feature.profile.dto.response.ProfileResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProfileMapper {

  public ProfileResponse mapToProfileResponse(MyProfileProjection profile);
}
