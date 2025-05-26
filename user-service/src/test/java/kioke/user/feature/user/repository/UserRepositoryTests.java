package kioke.user.feature.user.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.util.Optional;
import kioke.user.config.JpaTestConfig;
import kioke.user.feature.user.domain.User;
import kioke.user.feature.user.dto.data.UserDto;
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
public class UserRepositoryTests {

  private final UserRepository userRepository;

  @Autowired
  public UserRepositoryTests(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Container
  private static PostgreSQLContainer<?> postgreSQLContainer =
      new PostgreSQLContainer<>("postgres:17-alpine");

  private Faker faker = new Faker();

  private User user =
      new User(faker.internet().uuidv4(), faker.internet().emailAddress(), faker.name().fullName());

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
  }

  @AfterEach
  @Transactional
  private void cleanUp() {
    userRepository.deleteAll();
  }

  @Test
  public void test_getUserById_givenUserId_whenUserIdExists_thenReturnUserDto() throws Exception {
    log.info("\ntest_getUserById_givenUserId_whenUserIdExists_thenReturnUserDto start");
    assertEquals(Optional.of(UserDto.from(user)), userRepository.getUserById(user.getUserId()));
    log.info("test_getUserById_givenUserId_whenUserIdExists_thenReturnUserDto end\n");
  }

  @Test
  public void test_getUserById_givenUserId_whenUserIdDoesNotExist_thenReturnEmptyOptional()
      throws Exception {
    log.info("\ntest_getUserById_givenUserId_whenUserIdDoesNotExist_thenReturnEmptyOptional start");
    assertEquals(Optional.empty(), userRepository.getUserById("non-existing-id"));
    log.info("test_getUserById_givenUserId_whenUserIdDoesNotExist_thenReturnEmptyOptional end\n");
  }

  @Test
  public void test_getUserById_givenUserId_whenUserIdIsNull_thenThrowNullPointerException()
      throws Exception {
    log.info("\ntest_getUserById_givenUserId_whenUserIdIsNull_thenThrowNullPointerException start");
    assertThrows(
        NullPointerException.class,
        () -> {
          userRepository.getUserById(null);
        });
    log.info("test_getUserById_givenUserId_whenUserIdIsNull_thenThrowNullPointerException end\n");
  }
}
