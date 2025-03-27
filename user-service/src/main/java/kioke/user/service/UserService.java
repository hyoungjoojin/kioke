package kioke.user.service;

import kioke.user.exception.user.UserDoesNotExistException;
import kioke.user.exception.user.UserDoesNotExistException.UserIdentifierType;
import kioke.user.model.User;
import kioke.user.repository.UserRepository;
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
  public UserDetails loadUserByUsername(String uid) throws UsernameNotFoundException {
    return userRepository
        .findById(uid)
        .orElseThrow(() -> new UsernameNotFoundException("User not found."));
  }

  public User getUserById(String uid) throws UserDoesNotExistException {
    return userRepository
        .findById(uid)
        .orElseThrow(() -> new UserDoesNotExistException(UserIdentifierType.UID, uid));
  }
}
