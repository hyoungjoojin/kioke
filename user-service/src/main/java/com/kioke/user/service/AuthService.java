package com.kioke.user.service;

import com.kioke.user.dto.data.auth.RegisterUserDto;
import com.kioke.user.exception.user.UserNotFoundException;
import com.kioke.user.model.User;
import com.kioke.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {
  @Autowired @Lazy private UserRepository userRepository;
  @Autowired @Lazy private PasswordEncoder passwordEncoder;

  @Autowired @Lazy private AuthenticationManager authenticationManager;

  @Override
  public UserDetails loadUserByUsername(String uid) throws UsernameNotFoundException {
    return userRepository
        .findById(uid)
        .orElseThrow(() -> new UsernameNotFoundException("User " + uid + " not found."));
  }

  public User registerUser(RegisterUserDto registerUserDto) {
    User user =
        User.builder()
            .email(registerUserDto.getEmail())
            .password(passwordEncoder.encode(registerUserDto.getPassword()))
            .build();

    return userRepository.save(user);
  }

  public User authenticateUser(String email, String password) throws UserNotFoundException {
    User user =
        userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));

    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(user.getId(), password));

    return user;
  }
}
