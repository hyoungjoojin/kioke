package kioke.user.feature.user.repository;

import static kioke.user.feature.user.domain.QUser.user;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Objects;
import java.util.Optional;
import kioke.user.feature.user.dto.data.UserDto;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryCustomImpl implements UserRepositoryCustom {

  private final JPAQueryFactory jpaQueryFactory;

  public UserRepositoryCustomImpl(JPAQueryFactory jpaQueryFactory) {
    this.jpaQueryFactory = jpaQueryFactory;
  }

  private BooleanExpression userIdEq(String userId) {
    return userId != null ? user.userId.eq(userId) : null;
  }

  @Override
  public Optional<UserDto> getUserById(String userId) {
    Objects.requireNonNull(userId, "Parameter userId must not be null.");

    return Optional.ofNullable(
        jpaQueryFactory
            .select(Projections.constructor(UserDto.class, user.userId, user.email, user.name))
            .from(user)
            .where(userIdEq(userId))
            .fetchFirst());
  }
}
