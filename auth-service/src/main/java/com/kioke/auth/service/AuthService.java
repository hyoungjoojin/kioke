package com.kioke.auth.service;

import com.kioke.auth.constant.KiokeServices;
import com.kioke.auth.exception.ServiceFailedException;
import com.kioke.auth.exception.ServiceNotFoundException;
import com.kioke.auth.exception.UserAlreadyExistsException;
import com.kioke.auth.exception.UserDoesNotExistException;
import com.kioke.auth.model.User;
import com.kioke.auth.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class AuthService implements UserDetailsService {
  @Autowired @Lazy private UserRepository userRepository;
  @Autowired @Lazy private PasswordEncoder passwordEncoder;
  @Autowired @Lazy private WebClient.Builder webClientBuilder;
  @Autowired @Lazy private DiscoveryClientService discoveryClientService;
  @Autowired @Lazy private AuthenticationManager authenticationManager;

  public String registerUser(String email, String password)
      throws UserAlreadyExistsException, ServiceNotFoundException, ServiceFailedException {
    if (userRepository.findUserByEmail(email).isPresent()) {
      throw new UserAlreadyExistsException(email);
    }

    User user = User.builder().email(email).password(passwordEncoder.encode(password)).build();

    try {
      webClientBuilder
          .build()
          .post()
          .uri(discoveryClientService.getServiceUri(KiokeServices.USER_SERVICE))
          .exchangeToMono(
              response -> {
                if (response.statusCode().isSameCodeAs(HttpStatus.CREATED)) {
                  return Mono.empty();
                } else {
                  Mono<String> responseBody = response.bodyToMono(String.class);
                  log.error("Request to user service failed. " + responseBody.toString());

                  throw new WebClientResponseException(
                      response.statusCode(),
                      "User service has failed.",
                      response.headers().asHttpHeaders(),
                      responseBody.toString().getBytes(),
                      null,
                      null);
                }
              });
    } catch (WebClientResponseException e) {
      throw new ServiceFailedException();
    }

    userRepository.save(user);
    return user.getUid();
  }

  public String loginUser(String email, String password)
      throws UserDoesNotExistException, BadCredentialsException {
    User user =
        userRepository
            .findUserByEmail(email)
            .orElseThrow(() -> new UserDoesNotExistException(email));

    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(user.getUid(), password));

    return user.getUid();
  }

  @Override
  public UserDetails loadUserByUsername(String uid) throws UsernameNotFoundException {
    return userRepository
        .findById(uid)
        .orElseThrow(
            () -> {
              throw new UsernameNotFoundException(uid);
            });
  }
}
