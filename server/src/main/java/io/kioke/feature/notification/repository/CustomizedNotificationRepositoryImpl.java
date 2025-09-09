package io.kioke.feature.notification.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

@Repository
public class CustomizedNotificationRepositoryImpl implements CustomizedNotificationRepository {

  private final JPAQueryFactory jpaQueryFactory;

  public CustomizedNotificationRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
    this.jpaQueryFactory = jpaQueryFactory;
  }
}
