package kioke.journal.utils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import kioke.journal.model.Journal;
import kioke.journal.model.User;
import kioke.journal.model.UserJournalMetadata;
import kioke.journal.repository.JournalRepository;
import kioke.journal.repository.UserJournalMetadataRepository;
import kioke.journal.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
@Profile("dev")
public class MockDataGenerator implements CommandLineRunner {

  private final UserRepository userRepository;
  private final JournalRepository journalRepository;
  private final UserJournalMetadataRepository userJournalMetadataRepository;

  private final int USERS_COUNT = 1000;
  private final int MAXIMUM_JOURNALS_COUNT_PER_USER = 100;

  private final Random random = new Random();
  private final Faker faker = new Faker();

  @Value("${kioke.mock.data-generator.enabled:false}")
  private boolean enabled;

  public MockDataGenerator(
      UserRepository userRepository,
      JournalRepository journalRepository,
      UserJournalMetadataRepository userJournalMetadataRepository) {
    this.userRepository = userRepository;
    this.journalRepository = journalRepository;
    this.userJournalMetadataRepository = userJournalMetadataRepository;
  }

  @Override
  public void run(String... args) throws Exception {
    if (!enabled) {
      return;
    }

    List<User> users = generateUsers();
    for (int i = 0; i < users.size(); i++) {
      List<Journal> journals =
          generateJournals(users.get(i), random.nextInt(MAXIMUM_JOURNALS_COUNT_PER_USER));

      generateUserJournalMetadata(users.get(i), journals);
    }
  }

  @Transactional
  private List<User> generateUsers() {
    List<User> users = new ArrayList<User>(USERS_COUNT);
    for (int i = 0; i < USERS_COUNT; i++) {
      users.add(User.builder().userId(UUID.randomUUID().toString()).build());
    }

    return userRepository.saveAll(users);
  }

  @Transactional
  private List<Journal> generateJournals(User user, int journalsCount) {
    List<Journal> journals = new ArrayList<Journal>(journalsCount);
    for (int i = 0; i < journalsCount; i++) {
      journals.add(
          Journal.builder()
              .title(faker.book().title())
              .description(faker.lorem().sentence())
              .build());
    }

    return journalRepository.saveAll(journals);
  }

  @Transactional
  private void generateUserJournalMetadata(User user, List<Journal> journals) {
    List<UserJournalMetadata> userJournalMetadatas =
        new ArrayList<UserJournalMetadata>(journals.size());

    for (int i = 0; i < journals.size(); i++) {
      userJournalMetadatas.add(
          UserJournalMetadata.builder()
              .user(user)
              .journal(journals.get(i))
              .isBookmarked(faker.bool().bool())
              .lastViewed(Instant.now())
              .build());
    }

    userJournalMetadataRepository.saveAll(userJournalMetadatas);
  }
}
