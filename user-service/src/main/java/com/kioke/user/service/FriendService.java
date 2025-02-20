package com.kioke.user.service;

import com.kioke.user.exception.friend.FriendRelationAlreadyExistsException;
import com.kioke.user.exception.friend.FriendRequestAlreadySentException;
import com.kioke.user.model.Friend;
import com.kioke.user.model.User;
import com.kioke.user.repository.FriendRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class FriendService {
  @Autowired @Lazy private FriendRepository friendRepository;

  public void addFriend(User user, User friend)
      throws FriendRequestAlreadySentException, FriendRelationAlreadyExistsException {
    Optional<Friend> relationship = friendRepository.findByUserAndFriend(user, friend);

    if (!relationship.isEmpty()) {
      boolean isPending = relationship.get().getIsPending();
      if (isPending) {
        relationship.get().setIsPending(false);
        friendRepository.save(relationship.get());
        return;
      } else {
        throw new FriendRelationAlreadyExistsException();
      }
    }

    Optional<Friend> reverseRelationship = friendRepository.findByUserAndFriend(friend, user);
    if (reverseRelationship.isEmpty()) {
      Friend newRelationship = Friend.builder().user(friend).friend(user).isPending(true).build();
      friendRepository.save(newRelationship);
      return;
    }

    boolean isPending = reverseRelationship.get().getIsPending();
    if (isPending) {
      throw new FriendRequestAlreadySentException();
    } else {
      throw new FriendRelationAlreadyExistsException();
    }
  }

  public List<User> getFriends(User user) {
    List<User> inboundRelationships =
        friendRepository.findByFriend(user).stream()
            .filter(relationship -> !relationship.getIsPending())
            .map(relationship -> relationship.getUser())
            .toList();

    List<User> outboundRelationships =
        friendRepository.findByUser(user).stream()
            .filter(relationship -> !relationship.getIsPending())
            .map(relationship -> relationship.getFriend())
            .toList();

    return Stream.concat(inboundRelationships.stream(), outboundRelationships.stream()).toList();
  }
}
