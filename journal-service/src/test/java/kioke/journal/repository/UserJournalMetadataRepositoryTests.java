package kioke.journal.repository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import java.time.Instant;
import java.util.List;
import kioke.journal.configuration.JpaTestConfiguration;
import kioke.journal.constant.Role;
import kioke.journal.dto.data.journal.JournalPreviewDto;
import kioke.journal.model.Journal;
import kioke.journal.model.User;
import kioke.journal.model.UserJournalMetadata;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.junit.jupiter.api.AfterAll;
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

@DataJpaTest
@Testcontainers
@Import(JpaTestConfiguration.class)
@Slf4j
public class UserJournalMetadataRepositoryTests {

  private final UserRepository userRepository;
  private final JournalRepository journalRepository;
  private final UserJournalMetadataRepository userJournalMetadataRepository;

  @Autowired
  public UserJournalMetadataRepositoryTests(
      UserRepository userRepository,
      JournalRepository journalRepository,
      UserJournalMetadataRepository userJournalMetadataRepository) {
    this.userRepository = userRepository;
    this.journalRepository = journalRepository;
    this.userJournalMetadataRepository = userJournalMetadataRepository;
  }

  private Faker faker = new Faker();

  @Container
  private static PostgreSQLContainer<?> postgreSQLContainer =
      new PostgreSQLContainer<>("postgres:17-alpine");

  @BeforeAll
  private static void beforeAll() {
    postgreSQLContainer.start();
  }

  @AfterAll
  private static void afterAll() {
    postgreSQLContainer.stop();
  }

  @DynamicPropertySource
  private static void configureProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
    dynamicPropertyRegistry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
    dynamicPropertyRegistry.add("spring.datasource.username", postgreSQLContainer::getUsername);
    dynamicPropertyRegistry.add("spring.datasource.password", postgreSQLContainer::getPassword);
  }

  private User targetUser;
  private User otherUser;
  private User nonExistingUser;

  private Journal targetJournal;
  private Journal targetBookmarkedJournal;
  private Journal otherJournal;

  @BeforeEach
  private void setUp() {
    clearData();

    generateUsers();
    generateJournals();
    generateUserJournalMetadata();
  }

  @Test
  public void test_findAllJournalsByUser_userHasJournals_returnJournals() {
    List<JournalPreviewDto> journals =
        userJournalMetadataRepository.findAllJournalsByUser(targetUser.getUserId(), false);
    assertNotNull(journals);
    assertSame(journals.size(), 2);

    List<String> actual = journals.stream().map(JournalPreviewDto::journalId).toList();
    log.info(actual.toArray().toString());

    assertThat(
        actual,
        containsInAnyOrder(targetJournal.getJournalId(), targetBookmarkedJournal.getJournalId()));
  }

  @Test
  public void test_findAllJournalsByUser_userHasBookmarkedJournals_returnBookmarkedJournals() {
    List<JournalPreviewDto> journals =
        userJournalMetadataRepository.findAllJournalsByUser(targetUser.getUserId(), true);
    assertNotNull(journals);
    assertSame(journals.size(), 1);
    assertSame(journals.get(0).journalId(), targetBookmarkedJournal.getJournalId());
  }

  @Test
  public void test_findAllJournalsByUser_userIdDoesNotExist_returnJournals() {
    List<JournalPreviewDto> journals =
        userJournalMetadataRepository.findAllJournalsByUser(nonExistingUser.getUserId(), false);
    assertNotNull(journals);
    assertSame(journals.size(), 0);
  }

  @Transactional
  private void clearData() {
    userJournalMetadataRepository.deleteAll();
    userRepository.deleteAll();
    journalRepository.deleteAll();
  }

  @Transactional
  private void generateUsers() {
    targetUser = User.builder().userId("ef5ceb4b-4575-40d6-948c-8252ca9663b7").build();
    otherUser = User.builder().userId("9f409cb1-6b09-48c2-bf71-061dd962ca6b").build();
    nonExistingUser = User.builder().userId("7348d6db-6440-4aff-964f-933977ad7855").build();

    List<User> users = userRepository.saveAll(List.of(targetUser, otherUser));
    targetUser = users.get(0);
    otherUser = users.get(1);
  }

  @Transactional
  private void generateJournals() {
    targetJournal =
        Journal.builder().title(faker.book().title()).description(faker.lorem().sentence()).build();
    targetBookmarkedJournal =
        Journal.builder().title(faker.book().title()).description(faker.lorem().sentence()).build();
    otherJournal =
        Journal.builder().title(faker.book().title()).description(faker.lorem().sentence()).build();

    List<Journal> journals =
        journalRepository.saveAll(List.of(targetJournal, targetBookmarkedJournal, otherJournal));
    targetJournal = journals.get(0);
    targetBookmarkedJournal = journals.get(1);
    otherJournal = journals.get(2);
  }

  @Transactional
  private void generateUserJournalMetadata() {
    List<UserJournalMetadata> userJournalMetadata =
        List.of(
            UserJournalMetadata.builder()
                .user(targetUser)
                .journal(targetJournal)
                .role(Role.AUTHOR)
                .lastViewed(Instant.now())
                .isBookmarked(false)
                .build(),
            UserJournalMetadata.builder()
                .user(targetUser)
                .journal(targetBookmarkedJournal)
                .role(Role.AUTHOR)
                .lastViewed(Instant.now())
                .isBookmarked(true)
                .build(),
            UserJournalMetadata.builder()
                .user(otherUser)
                .journal(otherJournal)
                .role(Role.AUTHOR)
                .lastViewed(Instant.now())
                .isBookmarked(true)
                .build());

    userJournalMetadataRepository.saveAll(userJournalMetadata);
  }
}
