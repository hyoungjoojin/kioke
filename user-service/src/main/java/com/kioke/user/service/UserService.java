package com.kioke.user.service;

import com.kioke.user.dto.data.user.CreateUserDto;
import com.kioke.user.dto.external.journal.JournalServiceCreateUserRequestBodyDto;
import com.kioke.user.exception.discovery.ServiceFailedException;
import com.kioke.user.exception.discovery.ServiceNotFoundException;
import com.kioke.user.exception.user.UserDoesNotExistException;
import com.kioke.user.exception.user.UserDoesNotExistException.UserIdentifierType;
import com.kioke.user.model.User;
import com.kioke.user.repository.UserRepository;
import com.kioke.user.service.DiscoveryClientService.KiokeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class UserService {
  @Autowired @Lazy private DiscoveryClientService discoveryClientService;

  @Autowired @Lazy private UserRepository userRepository;

  private RestClient restClient = RestClient.create();

  public void createUser(CreateUserDto data)
      throws ServiceNotFoundException, ServiceFailedException {
    User user =
        User.builder()
            .uid(data.getUid())
            .email(data.getEmail())
            .firstName(data.getFirstName())
            .lastName(data.getLastName())
            .build();

    restClient
        .post()
        .uri(discoveryClientService.getServiceUri(KiokeService.JOURNAL) + "/users")
        .body(new JournalServiceCreateUserRequestBodyDto(user.getUid()))
        .retrieve()
        .onStatus(
            status -> status != HttpStatus.CREATED,
            (req, res) -> {
              throw new ServiceFailedException();
            })
        .toBodilessEntity();

    userRepository.save(user);
  }

  public User getUserById(String uid) throws UserDoesNotExistException {
    return userRepository
        .findById(uid)
        .orElseThrow(() -> new UserDoesNotExistException(UserIdentifierType.UID, uid));
  }
}
