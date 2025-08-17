package io.kioke.feature.journal.repository;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;
import static io.kioke.feature.journal.domain.QJournal.journal;
import static io.kioke.feature.journal.domain.QJournalCollection.journalCollection;
import static io.kioke.feature.journal.domain.QJournalPermission.journalPermission;
import static io.kioke.feature.page.domain.QPage.page;

import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.kioke.feature.journal.dto.JournalCollectionDto;
import io.kioke.feature.journal.dto.JournalDto;
import io.kioke.feature.journal.dto.JournalPermissionDto;
import io.kioke.feature.user.dto.UserDto;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

class CustomizedJournalRepositoryImpl implements CustomizedJournalRepository {

  private final JPAQueryFactory jpaQueryFactory;

  public CustomizedJournalRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
    this.jpaQueryFactory = jpaQueryFactory;
  }

  @Override
  public Optional<JournalDto> findByJournalId(String journalId) {
    ConstructorExpression<JournalDto.Page> pageConstructor =
        Projections.constructor(JournalDto.Page.class, page.pageId, page.title, page.date);

    ConstructorExpression<JournalDto> journalConstructor =
        Projections.constructor(
            JournalDto.class,
            journal.journalId,
            journal.title,
            journal.description,
            journal.isPublic,
            list(pageConstructor));

    JournalDto result =
        jpaQueryFactory
            .from(journal)
            .where(journalIdEq(journalId))
            .join(journal.pages, page)
            .transform(groupBy(journal.journalId).as(journalConstructor))
            .get(journalId);

    return Optional.ofNullable(result);
  }

  @Override
  public Optional<JournalCollectionDto> findCollectionByUserAndId(
      UserDto user, String collectionId) {
    ConstructorExpression<JournalCollectionDto.Journal> journalConstructor =
        Projections.constructor(
            JournalCollectionDto.Journal.class, journal.journalId, journal.title);

    ConstructorExpression<JournalCollectionDto> collectionConstructor =
        Projections.constructor(
            JournalCollectionDto.class,
            journalCollection.collectionId,
            journalCollection.name,
            list(journalConstructor),
            journalCollection.isDefaultCollection);

    JournalCollectionDto result =
        jpaQueryFactory
            .from(journalCollection)
            .leftJoin(journalCollection.journals, journal)
            .where(collectionIdEq(collectionId), userIdEq(user.userId()))
            .transform(groupBy(journalCollection.collectionId).as(collectionConstructor))
            .get(collectionId);

    return Optional.ofNullable(result);
  }

  @Override
  public List<JournalCollectionDto> findCollectionsByUser(UserDto user) {
    ConstructorExpression<JournalCollectionDto.Journal> journalConstructor =
        Projections.constructor(JournalCollectionDto.Journal.class, journal.journalId);

    ConstructorExpression<JournalCollectionDto> collectionConstructor =
        Projections.constructor(
            JournalCollectionDto.class,
            journalCollection.collectionId,
            journalCollection.name,
            list(journalConstructor),
            journalCollection.isDefaultCollection);

    Collection<JournalCollectionDto> collections =
        jpaQueryFactory
            .from(journalCollection)
            .leftJoin(journalCollection.journals, journal)
            .where(userIdEq(user.userId()))
            .transform(groupBy(journalCollection.collectionId).as(collectionConstructor))
            .values();

    return new ArrayList<>(collections);
  }

  @Override
  public Optional<JournalPermissionDto> findPermissions(UserDto user, JournalDto journalDto) {
    JournalPermissionDto result =
        jpaQueryFactory
            .select(
                Projections.constructor(
                    JournalPermissionDto.class,
                    journal.isPublic,
                    journalPermission.canRead,
                    journalPermission.canEdit,
                    journalPermission.canDelete))
            .from(journalPermission)
            .where(permissionUserIdEq(user.userId()), permissionJournalIdEq(journalDto.journalId()))
            .rightJoin(journalPermission.journal, journal)
            .fetchFirst();

    return Optional.ofNullable(result);
  }

  private static BooleanExpression journalIdEq(String journalId) {
    return journalId != null ? journal.journalId.eq(journalId) : null;
  }

  private static BooleanExpression collectionIdEq(String collectionId) {
    return collectionId == null ? null : journalCollection.collectionId.eq(collectionId);
  }

  private static BooleanExpression userIdEq(String userId) {
    return userId != null ? journalCollection.user.userId.eq(userId) : null;
  }

  private static BooleanExpression permissionUserIdEq(String userId) {
    return userId != null ? journalPermission.user.userId.eq(userId) : null;
  }

  private static BooleanExpression permissionJournalIdEq(String journalId) {
    return journalId != null ? journalPermission.journal.journalId.eq(journalId) : null;
  }
}
