package io.kioke.feature.page.repository;

import io.kioke.feature.page.domain.Page;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
class CustomPageRepositoryImpl implements CustomPageRepository {

  @PersistenceContext private EntityManager entityManager;

  @Override
  public Optional<Page> findWithBlocksById(String pageId) {
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<Page> query = criteriaBuilder.createQuery(Page.class);

    Root<Page> root = query.from(Page.class);
    root.fetch("blocks", JoinType.LEFT);

    List<Predicate> predicates = new ArrayList<>();
    predicates.add(criteriaBuilder.equal(root.get("pageId"), pageId));
    query.where(predicates.toArray(Predicate[]::new));

    return Optional.ofNullable(entityManager.createQuery(query.select(root)).getSingleResult());
  }
}
