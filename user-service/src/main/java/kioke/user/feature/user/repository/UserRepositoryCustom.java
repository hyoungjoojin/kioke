package kioke.user.feature.user.repository;

import java.util.Optional;
import kioke.user.feature.user.dto.data.UserDto;

public interface UserRepositoryCustom {

  public Optional<UserDto> getUserById(String userId);
}
