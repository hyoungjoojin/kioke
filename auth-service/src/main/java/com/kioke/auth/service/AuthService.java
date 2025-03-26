package com.kioke.auth.service;

import com.kioke.auth.dto.data.auth.RegisterUserDto;
import com.kioke.auth.exception.ServiceFailedException;
import com.kioke.auth.exception.ServiceNotFoundException;
import com.kioke.auth.exception.UserAlreadyExistsException;
import com.kioke.auth.exception.UserDoesNotExistException;
import com.kioke.auth.exception.UserDoesNotExistException.UserIdentifierType;
import com.kioke.auth.model.User;
import com.kioke.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
  @Autowired @Lazy private UserRepository userRepository;

  @Autowired @Lazy private AuthenticationManager authenticationManager;
  @Autowired @Lazy private PasswordEncoder passwordEncoder;

  public User registerUser(RegisterUserDto data)
      throws UserAlreadyExistsException, ServiceNotFoundException, ServiceFailedException {
    if (userRepository.findUserByEmail(data.getEmail()).isPresent()) {
      throw new UserAlreadyExistsException(data.getEmail());
    }

    String encodedPassword = passwordEncoder.encode(data.getPassword());
    User user = User.builder().email(data.getEmail()).password(encodedPassword).build();

    return userRepository.save(user);
  }

  public User loginUser(String email, String password)
      throws UserDoesNotExistException, BadCredentialsException {
    User user =
        userRepository
            .findUserByEmail(email)
            .orElseThrow(() -> new UserDoesNotExistException(UserIdentifierType.EMAIL, email));

    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(user.getUid(), password));

    return user;
  }
}
