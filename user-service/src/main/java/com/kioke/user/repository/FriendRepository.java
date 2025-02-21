package com.kioke.user.repository;

import com.kioke.user.model.Friend;
import com.kioke.user.model.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRepository extends JpaRepository<Friend, String> {
  public List<Friend> findByUser(User user);

  public List<Friend> findByFriend(User friend);

  public Optional<Friend> findByUserAndFriend(User user, User friend);
}
