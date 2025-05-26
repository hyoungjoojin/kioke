package kioke.user.feature.preferences.repository;

import static kioke.user.feature.preferences.domain.QUserPreferences.userPreferences;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import kioke.user.feature.preferences.dto.data.UserPreferencesDto;

public class UserPreferencesRepositoryCustomImpl implements UserPreferencesRepositoryCustom {

  private final JPAQueryFactory jpaQueryFactory;

  public UserPreferencesRepositoryCustomImpl(JPAQueryFactory jpaQueryFactory) {
    this.jpaQueryFactory = jpaQueryFactory;
  }

  @Override
  public Optional<UserPreferencesDto> getUserPreferencesByUserId(String userId) {
    return Optional.ofNullable(
        jpaQueryFactory
            .select(Projections.constructor(UserPreferencesDto.class, userPreferences.theme))
            .from(userPreferences)
            .where(userIdEquals(userId))
            .fetchFirst());
  }

  private static BooleanExpression userIdEquals(String userId) {
    return userId != null ? userPreferences.id.eq(userId) : null;
  }
}
