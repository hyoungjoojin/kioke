package kioke.journal.service;

import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.Optional;
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

  public User createUser(String uid) {
    Optional<User> user = userRepository.findById(uid);
    if (user.isPresent()) {
      return user.get();
    }

    User newUser = User.builder().uid(uid).journals(new ArrayList<>()).build();
    newUser = userRepository.save(newUser);
    return newUser;
  }

  public User getUserById(String uid) throws UserNotFoundException {
    return userRepository.findById(uid).orElseThrow(() -> new UserNotFoundException());
  }
}
