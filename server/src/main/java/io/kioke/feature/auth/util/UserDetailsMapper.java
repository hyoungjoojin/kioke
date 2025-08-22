package io.kioke.feature.auth.util;

import io.kioke.feature.auth.domain.UserDetails;
import io.kioke.feature.auth.dto.UserDetailsDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserDetailsMapper {

  public UserDetailsDto toDto(UserDetails userDetails);
}
