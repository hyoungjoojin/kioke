package kioke.user.feature.user.service;

import java.util.Objects;
import java.util.Optional;
import kioke.user.feature.user.dto.data.UserDto;
import kioke.user.feature.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService implements UserDetailsService {

  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Transactional(readOnly = true)
  public Optional<UserDto> getUserById(String userId) {
    Objects.requireNonNull(userId, "Parameter userId must not be null.");

    return userRepository.getUserById(userId);
  }

  @Override
  @Transactional(readOnly = true)
  public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
    return userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException(""));
  }
}
