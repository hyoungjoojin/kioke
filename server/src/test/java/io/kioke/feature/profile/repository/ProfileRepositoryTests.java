package io.kioke.feature.profile.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.kioke.config.JpaTestConfig;
import io.kioke.feature.profile.domain.Profile;
import io.kioke.feature.profile.dto.ProfileDto;
import io.kioke.feature.user.domain.User;
import io.kioke.util.SeedDataProvider;
import java.util.Optional;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@DataJpaTest
@Testcontainers
@Import(JpaTestConfig.class)
public class ProfileRepositoryTests {

  @Container
  private static final PostgreSQLContainer<?> postgreSQLContainer =
      new PostgreSQLContainer<>("postgres:17-alpine");

  @Autowired private ProfileRepository profileRepository;

  @DynamicPropertySource
  private static void configureProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
    registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
    registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
  }

  @BeforeAll
  private static void beforeAll() {
    postgreSQLContainer.start();
  }

  @AfterAll
  private static void afterAll() {
    postgreSQLContainer.stop();
  }

  @Test
  public void findByUserId_userIsNull_returnEmptyOptional() {
    assertEquals(Optional.empty(), profileRepository.findByUserId(null));
  }

  @Test
  public void findByUserId_userDoesNotExist_returnEmptyOptional() {
    assertEquals(Optional.empty(), profileRepository.findByUserId("user"));
  }

  @Test
  @Sql("classpath:/db/seed.sql")
  public void findByUserId_userExists_returnUserProfile() {
    User user = SeedDataProvider.user1;
    Profile profile = SeedDataProvider.user1Profile;

    ProfileDto expected =
        new ProfileDto(
            user.getEmail(),
            profile.getName(),
            profile.isOnboarded(),
            profile.getCreatedAt(),
            profile.getLastModifiedAt());

    ProfileDto actual = profileRepository.findByUserId(SeedDataProvider.user1.getUserId()).get();

    assertEquals(expected, actual);
  }
}
