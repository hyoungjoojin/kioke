package kioke.journal.dto.data.user;

import kioke.journal.constant.Role;
import kioke.journal.model.JournalRole;

public record UserDto(String userId, Role role) {

  public static UserDto from(JournalRole journalRole) {
    return new UserDto(journalRole.getUser().getUserId(), journalRole.getRole());
  }
}
