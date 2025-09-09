package io.kioke.feature.friend.repository;

import io.kioke.feature.friend.domain.Friendship;
import io.kioke.feature.friend.dto.FriendRequestDto;
import io.kioke.feature.user.domain.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FriendRepository extends JpaRepository<Friendship, String> {

  @Query(
      "SELECT f FROM Friendship f "
          + "WHERE f.userA = :user AND f.status = 'ACCEPTED'"
          + "UNION "
          + "SELECT f FROM Friendship f "
          + "WHERE f.userB = :user AND f.status = 'ACCEPTED'")
  public List<Friendship> findByUserAndAccepted(User user);

  @Query(
      "SELECT COUNT(f) > 0 FROM Friendship f "
          + "WHERE (f.userA = :userA AND f.userB = :userB) "
          + "OR (f.userA = :userB AND f.userB = :userA)")
  public Boolean existsByUsers(User userA, User userB);

  @Query(
      "SELECT f.id, f.userA.userId, f.userB.userId, f.status "
          + "FROM Friendship f WHERE f.userB = :user")
  public List<FriendRequestDto> findIncomingFriendships(User user);

  @Query(
      "SELECT f.id, f.userA.userId, f.userB.userId, f.status  "
          + "FROM Friendship f WHERE f.userA = :user")
  public List<FriendRequestDto> findOutgoingFriendships(User user);
}
