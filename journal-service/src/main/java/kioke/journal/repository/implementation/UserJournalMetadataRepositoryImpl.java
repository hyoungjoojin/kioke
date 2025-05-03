package kioke.journal.repository.implementation;

import static kioke.journal.model.QJournal.journal;
import static kioke.journal.model.QUserJournalMetadata.userJournalMetadata;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import kioke.journal.dto.data.journal.JournalPreviewDto;
import kioke.journal.model.UserJournalMetadata;
import kioke.journal.repository.custom.UserJournalMetadataRepositoryCustom;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class UserJournalMetadataRepositoryImpl implements UserJournalMetadataRepositoryCustom {

  private final JPAQueryFactory jpaQueryFactory;

  public UserJournalMetadataRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
    this.jpaQueryFactory = jpaQueryFactory;
  }

  @Override
  public List<JournalPreviewDto> findAllJournalsByUser(
      String userId, boolean findOnlyBookmarkedJournals) {
    if (userId == null) {
      return Collections.emptyList();
    }

    BooleanBuilder booleanBuilder =
        new BooleanBuilder().and(userJournalMetadata.user.userId.eq(userId));
    if (findOnlyBookmarkedJournals) {
      booleanBuilder.and(userJournalMetadata.isBookmarked.eq(true));
    }

    return jpaQueryFactory
        .select(
            Projections.constructor(
                JournalPreviewDto.class,
                userJournalMetadata.journal.journalId,
                journal.title,
                userJournalMetadata.isBookmarked,
                journal.createdAt))
        .from(userJournalMetadata)
        .join(userJournalMetadata.journal, journal)
        .where(booleanBuilder)
        .fetch();
  }

  @Override
  public Optional<UserJournalMetadata> findByUserIdAndJournalId(String userId, String journalId) {
    UserJournalMetadata result =
        jpaQueryFactory
            .selectFrom(userJournalMetadata)
            .where(
                userJournalMetadata
                    .user
                    .userId
                    .eq(userId)
                    .and(userJournalMetadata.journal.journalId.eq(journalId)))
            .fetchOne();

    return Optional.ofNullable(result);
  }
}
