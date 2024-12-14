package com.kioke.auth.service;

import com.kioke.auth.constant.KiokeServices;
import com.kioke.auth.dto.external.user.CreateUserRequestBodyDto;
import com.kioke.auth.exception.ServiceNotFoundException;
import com.kioke.auth.exception.UserAlreadyExistsException;
import com.kioke.auth.exception.UserDoesNotExistException;
import com.kioke.auth.model.User;
import com.kioke.auth.repository.UserRepository;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

@Service
@Slf4j
public class AuthService {
  @Autowired @Lazy private UserRepository userRepository;

  @Autowired @Lazy private AuthenticationManager authenticationManager;
  @Autowired @Lazy private PasswordEncoder passwordEncoder;

  @Autowired @Lazy private DiscoveryClientService discoveryClientService;
  private RestClient restClient = RestClient.create();

  public User registerUser(String email, String password)
      throws UserAlreadyExistsException, ServiceNotFoundException, RestClientResponseException {
    if (userRepository.findUserByEmail(email).isPresent()) {
      throw new UserAlreadyExistsException(email);
    }

    String uid = generateUuid();
    String encodedPassword = passwordEncoder.encode(password);
    User user = User.builder().uid(uid).email(email).password(encodedPassword).build();

    String userServiceUri = discoveryClientService.getServiceUri(KiokeServices.USER_SERVICE);
    restClient
        .post()
        .uri(userServiceUri + "/users")
        .contentType(MediaType.APPLICATION_JSON)
        .body(new CreateUserRequestBodyDto(uid, email))
        .retrieve()
        .onStatus(
            status -> !status.isSameCodeAs(HttpStatus.CREATED),
            (request, response) -> {
              throw new RestClientResponseException(
                  "User service has failed to create a new user.",
                  response.getStatusCode(),
                  response.getStatusText(),
                  response.getHeaders(),
                  response.getBody().readAllBytes(),
                  null);
            });

    userRepository.save(user);
    return user;
  }

  public User loginUser(String email, String password)
      throws UserDoesNotExistException, BadCredentialsException {
    User user =
        userRepository
            .findUserByEmail(email)
            .orElseThrow(() -> new UserDoesNotExistException(email));

    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(user.getUid(), password));

    return user;
  }

  private String generateUuid() {
    while (true) {
      String uuid = UUID.randomUUID().toString();
      if (!userRepository.findById(uuid).isPresent()) {
        return uuid;
      }
    }
  }
}
