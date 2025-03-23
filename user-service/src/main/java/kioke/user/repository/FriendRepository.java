package kioke.user.repository;

import java.util.List;
import java.util.Optional;
import kioke.user.model.Friend;
import kioke.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRepository extends JpaRepository<Friend, String> {
  public List<Friend> findByUser(User user);

  public List<Friend> findByFriend(User friend);

  public Optional<Friend> findByUserAndFriend(User user, User friend);
}
