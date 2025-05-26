package kioke.user.feature.preferences.repository;

import static org.junit.Assert.assertEquals;

import java.util.Optional;
import kioke.user.config.JpaTestConfig;
import kioke.user.feature.preferences.domain.UserPreferences;
import kioke.user.feature.preferences.dto.data.UserPreferencesDto;
import kioke.user.feature.user.domain.User;
import kioke.user.feature.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@DataJpaTest(showSql = true)
@Testcontainers
@Import({JpaTestConfig.class})
@Slf4j
public class UserPreferencesRepositoryTests {

  private final UserRepository userRepository;
  private final UserPreferencesRepository userPreferencesRepository;

  @Autowired
  public UserPreferencesRepositoryTests(
      UserRepository userRepository, UserPreferencesRepository userPreferencesRepository) {
    this.userRepository = userRepository;
    this.userPreferencesRepository = userPreferencesRepository;
  }

  @Container
  private static PostgreSQLContainer<?> postgreSQLContainer =
      new PostgreSQLContainer<>("postgres:17-alpine");

  private Faker faker = new Faker();

  private User user =
      new User(faker.internet().uuidv4(), faker.internet().emailAddress(), faker.name().fullName());
  private UserPreferences userPreferences;

  @BeforeAll
  private static void beforeAll() {
    postgreSQLContainer.start();
  }

  @DynamicPropertySource
  private static void configureProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
    dynamicPropertyRegistry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
    dynamicPropertyRegistry.add("spring.datasource.username", postgreSQLContainer::getUsername);
    dynamicPropertyRegistry.add("spring.datasource.password", postgreSQLContainer::getPassword);
  }

  @AfterAll
  private static void afterAll() {
    postgreSQLContainer.stop();
  }

  @BeforeEach
  @Transactional
  private void setUp() {
    user = userRepository.save(user);

    userPreferences = new UserPreferences();
    userPreferences.setUser(user);
    userPreferencesRepository.save(userPreferences);
  }

  @AfterEach
  @Transactional
  private void cleanUp() {
    userRepository.deleteAll();
    userPreferencesRepository.deleteAll();
  }

  @Test
  public void test_getUserPreferencesByUserId_givenUserId_whenUserIdExists_thenReturnPreferences()
      throws Exception {
    log.info(
        "\n"
            + "test_getUserPreferencesByUserId_givenUserId_whenUserIdExists_thenReturnPreferences"
            + " start");
    assertEquals(
        Optional.of(UserPreferencesDto.from(userPreferences)),
        userPreferencesRepository.getUserPreferencesByUserId(user.getUserId()));
    log.info(
        "test_getUserPreferencesByUserId_givenUserId_whenUserIdExists_thenReturnPreferences end\n");
  }
}
