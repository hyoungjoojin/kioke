package io.kioke.feature.profile.util;

import io.kioke.feature.preferences.dto.PreferencesDto;
import io.kioke.feature.profile.domain.Profile;
import io.kioke.feature.profile.dto.ProfileDto;
import io.kioke.feature.profile.dto.response.GetMyProfileResponse;
import io.kioke.feature.profile.dto.response.GetProfileResponseDto;
import io.kioke.feature.profile.dto.response.SearchProfilesResponseDto;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProfileMapper {

  @Mapping(source = "profile.user.email", target = "email")
  public ProfileDto toDto(Profile profile);

  public GetMyProfileResponse toGetMyProfileResponse(
      ProfileDto profile, PreferencesDto preferences);

  public GetProfileResponseDto toGetProfileResponse(ProfileDto profile);

  public SearchProfilesResponseDto toSearchProfilesResponse(
      String query, List<ProfileDto> profiles);
}
