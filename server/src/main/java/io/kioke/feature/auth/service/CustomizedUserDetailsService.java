package io.kioke.feature.auth.service;

import io.kioke.feature.auth.repository.UserDetailsRepository;
import io.kioke.feature.auth.util.UserDetailsMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
class CustomizedUserDetailsService implements UserDetailsService {

  private final UserDetailsRepository userDetailsRepository;
  private final UserDetailsMapper userDetailsMapper;

  public CustomizedUserDetailsService(
      UserDetailsRepository userDetailsRepository, UserDetailsMapper userDetailsMapper) {
    this.userDetailsRepository = userDetailsRepository;
    this.userDetailsMapper = userDetailsMapper;
  }

  @Override
  @Transactional(readOnly = true)
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userDetailsRepository
        .findById(username)
        .map(userDetailsMapper::toDto)
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
  }
}
