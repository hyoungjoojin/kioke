package com.kioke.auth.service;

import com.kioke.auth.constant.KiokeServices;
import com.kioke.auth.dto.data.auth.RegisterUserDto;
import com.kioke.auth.dto.external.user.CreateUserRequestBodyDto;
import com.kioke.auth.exception.ServiceFailedException;
import com.kioke.auth.exception.ServiceNotFoundException;
import com.kioke.auth.exception.UserAlreadyExistsException;
import com.kioke.auth.exception.UserDoesNotExistException;
import com.kioke.auth.exception.UserDoesNotExistException.UserIdentifierType;
import com.kioke.auth.model.User;
import com.kioke.auth.repository.UserRepository;
import java.util.UUID;
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

@Service
public class AuthService {
  @Autowired @Lazy private UserRepository userRepository;

  @Autowired @Lazy private AuthenticationManager authenticationManager;
  @Autowired @Lazy private PasswordEncoder passwordEncoder;

  @Autowired @Lazy private DiscoveryClientService discoveryClientService;
  private RestClient restClient = RestClient.create();

  public User registerUser(RegisterUserDto data)
      throws UserAlreadyExistsException, ServiceNotFoundException, ServiceFailedException {
    String email = data.getEmail(), password = data.getPassword();

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
        .body(new CreateUserRequestBodyDto(uid, email, data.getFirstName(), data.getLastName()))
        .retrieve()
        .onStatus(
            status -> status != HttpStatus.CREATED,
            (req, res) -> {
              throw new ServiceFailedException(
                  KiokeServices.USER_SERVICE, res.getStatusCode(), res.getBody().toString());
            })
        .toBodilessEntity();

    userRepository.save(user);
    return user;
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

  private String generateUuid() {
    while (true) {
      String uuid = UUID.randomUUID().toString();
      if (!userRepository.findById(uuid).isPresent()) {
        return uuid;
      }
    }
  }
}
