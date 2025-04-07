package kioke.journal.service;

import kioke.journal.model.User;
import kioke.journal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
  @Autowired @Lazy private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
    return getUserById(userId);
  }

  public User getUserById(String userId) throws UsernameNotFoundException {
    return userRepository
        .findById(userId)
        .orElseThrow(
            () -> new UsernameNotFoundException("User with ID " + userId + " cannot be found."));
  }
}
