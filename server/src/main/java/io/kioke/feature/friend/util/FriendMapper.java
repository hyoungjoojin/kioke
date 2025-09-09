package io.kioke.feature.friend.util;

import io.kioke.feature.friend.domain.Friendship;
import io.kioke.feature.friend.dto.FriendDto;
import io.kioke.feature.friend.dto.response.GetFriendsResponseDto;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FriendMapper {

  public FriendDto toDto(Friendship friendship);

  public GetFriendsResponseDto toGetFriendsResponse(int count, List<FriendDto> friends);
}
