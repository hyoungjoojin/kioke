package kioke.user.service;

import kioke.user.dto.data.user.CreateUserDto;
import kioke.user.dto.external.journal.JournalServiceCreateUserRequestBodyDto;
import kioke.user.exception.discovery.ServiceFailedException;
import kioke.user.exception.discovery.ServiceNotFoundException;
import kioke.user.exception.user.UserDoesNotExistException;
import kioke.user.exception.user.UserDoesNotExistException.UserIdentifierType;
import kioke.user.model.User;
import kioke.user.repository.UserRepository;
import kioke.user.service.DiscoveryClientService.KiokeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class UserService implements UserDetailsService {
  @Autowired @Lazy private DiscoveryClientService discoveryClientService;

  @Autowired @Lazy private UserRepository userRepository;

  private RestClient restClient = RestClient.create();

  @Override
  public UserDetails loadUserByUsername(String uid) throws UsernameNotFoundException {
    return userRepository
        .findById(uid)
        .orElseThrow(() -> new UsernameNotFoundException("User not found."));
  }

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
