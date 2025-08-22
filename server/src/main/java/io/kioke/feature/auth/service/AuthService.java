package io.kioke.feature.auth.service;

import io.kioke.feature.auth.domain.UserDetails;
import io.kioke.feature.auth.dto.UserDetailsDto;
import io.kioke.feature.auth.repository.UserDetailsRepository;
import io.kioke.feature.auth.util.UserDetailsMapper;
import io.kioke.feature.user.dto.UserDto;
import java.util.Collection;
import java.util.Collections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

  private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

  private final UserDetailsRepository userDetailsRepository;
  private final UserDetailsMapper userDetailsMapper;
  private final AuthenticationManager authenticationManager;
  private final PasswordEncoder passwordEncoder;

  public AuthService(
      UserDetailsRepository userDetailsRepository,
      UserDetailsMapper userDetailsMapper,
      AuthenticationManager authenticationManager,
      PasswordEncoder passwordEncoder) {
    this.userDetailsRepository = userDetailsRepository;
    this.userDetailsMapper = userDetailsMapper;
    this.authenticationManager = authenticationManager;
    this.passwordEncoder = passwordEncoder;
  }

  @Transactional
  public void createUserDetails(UserDto user, String password) {
    UserDetails userDetails = UserDetails.from(user.userId(), passwordEncoder.encode(password));
    userDetailsRepository.save(userDetails);
  }

  @Transactional(readOnly = true)
  public Authentication authenticate(String email, String password) throws AuthenticationException {
    UserDetailsDto userDetails =
        userDetailsRepository
            .findByEmail(email)
            .map(userDetailsMapper::toDto)
            .orElseThrow(
                () -> {
                  logger.debug(
                      "User with email {} not found, throwing BadCredentialsException", email);
                  return new BadCredentialsException("User not found");
                });

    String principal = userDetails.userId();
    Collection<? extends GrantedAuthority> authorities = Collections.emptyList();

    Authentication authentication =
        UsernamePasswordAuthenticationToken.authenticated(principal, password, authorities);
    authenticationManager.authenticate(authentication);
    return authentication;
  }
}
