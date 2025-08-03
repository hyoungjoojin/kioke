package io.kioke.feature.user.mapper;

import io.kioke.feature.user.domain.User;
import io.kioke.feature.user.dto.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

  public UserDto toUserDto(User user);
}
