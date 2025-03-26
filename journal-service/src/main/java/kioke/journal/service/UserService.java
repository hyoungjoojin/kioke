package kioke.journal.service;

import jakarta.transaction.Transactional;
import kioke.journal.exception.user.UserNotFoundException;
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
  @Transactional
  public UserDetails loadUserByUsername(String uid) throws UsernameNotFoundException {
    return userRepository
        .findById(uid)
        .orElseThrow(() -> new UsernameNotFoundException("User not found."));
  }

  public User getUserById(String uid) throws UserNotFoundException {
    return userRepository.findById(uid).orElseThrow(() -> new UserNotFoundException());
  }
}
