package io.kioke.feature.friend.service;

import io.kioke.exception.friend.FriendshipAlreadyExistsException;
import io.kioke.feature.friend.domain.Friendship;
import io.kioke.feature.friend.dto.FriendDto;
import io.kioke.feature.friend.dto.request.SendFriendRequestDto;
import io.kioke.feature.friend.repository.FriendRepository;
import io.kioke.feature.friend.util.FriendMapper;
import io.kioke.feature.notification.constant.NotificationType;
import io.kioke.feature.notification.domain.content.FriendRequestNotificationContent;
import io.kioke.feature.notification.service.NotificationService;
import io.kioke.feature.user.domain.User;
import io.kioke.feature.user.dto.UserDto;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FriendService {

  private final NotificationService notificationService;
  private final FriendRepository friendRepository;
  private final FriendMapper friendMapper;

  public FriendService(
      NotificationService notificationService,
      FriendRepository friendRepository,
      FriendMapper friendMapper) {
    this.notificationService = notificationService;
    this.friendRepository = friendRepository;
    this.friendMapper = friendMapper;
  }

  @Transactional
  public void sendFriendRequest(UserDto user, SendFriendRequestDto request)
      throws FriendshipAlreadyExistsException {
    User userA = User.of(user.userId()), userB = User.of(request.userId());

    if (friendRepository.existsByUsers(userA, userB)) {
      throw new FriendshipAlreadyExistsException();
    }

    Friendship friendship = Friendship.createPending(userA, userB);
    friendRepository.save(friendship);
    notificationService.createNotification(
        userB, NotificationType.FRIEND_REQUEST, FriendRequestNotificationContent.of(friendship));
  }

  @Transactional(readOnly = true)
  public List<FriendDto> getFriends(UserDto user) {
    return friendRepository
        .findByUserAndAccepted(User.builder().userId(user.userId()).build())
        .stream()
        .map(friendMapper::toDto)
        .toList();
  }

  //
  // @Transactional(readOnly = true)
  // public List<FriendRequestDto> getIncomingFriendRequests(UserDto user) {
  //   User userReference = new User();
  //   userReference.setUserId(user.userId());
  //   return friendRepository.findIncomingFriendships(userReference);
  // }
  //
  // @Transactional(readOnly = true)
  // public List<FriendRequestDto> getOutgoingFriendRequests(UserDto user) {
  //   User userReference = new User();
  //   userReference.setUserId(user.userId());
  //   return friendRepository.findOutgoingFriendships(userReference);
  // }
  //
  //
  // @Transactional
  // public void updatefriendRequest(UserDto user, String requestId, FriendshipStatus status)
  //     throws FriendRequestDoesNotExistException {
  //   Friendship friendship =
  //       friendRepository
  //           .findById(requestId)
  //           .orElseThrow(() -> new FriendRequestDoesNotExistException());
  //
  //   friendship.setStatus(status);
  //   friendRepository.save(friendship);
  // }
}
