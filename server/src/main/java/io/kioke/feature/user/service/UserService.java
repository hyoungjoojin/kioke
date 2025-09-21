package io.kioke.feature.user.service;

import io.kioke.exception.user.UserAlreadyExistsException;
import io.kioke.feature.profile.service.ProfileService;
import io.kioke.feature.user.domain.User;
import io.kioke.feature.user.repository.UserRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
public class UserService {

  private final UserRepository userRepository;

  private final ProfileService profileService;

  public UserService(UserRepository userRepository, ProfileService profileService) {
    this.userRepository = userRepository;
    this.profileService = profileService;
  }

  @Transactional(readOnly = true)
  public User getUserReference(String userId) {
    return userRepository.getReferenceById(userId);
  }

  @Transactional
  public User createUser(String email) throws UserAlreadyExistsException {
    Assert.notNull(email, "Email must not be null");

    if (userRepository.findByEmail(email).isPresent()) {
      throw new UserAlreadyExistsException();
    }

    User user = User.builder().email(email).build();
    user = userRepository.save(user);

    profileService.createProfile(user);
    return user;
  }

  @Transactional(readOnly = true)
  public Optional<User> findById(String userId) {
    return userRepository.findById(userId);
  }
}
