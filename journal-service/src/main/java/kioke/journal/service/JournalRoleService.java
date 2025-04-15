package kioke.journal.service;

import java.util.List;
import java.util.Objects;
import kioke.journal.constant.Permission;
import kioke.journal.constant.Role;
import kioke.journal.model.Journal;
import kioke.journal.model.JournalRole;
import kioke.journal.model.User;
import kioke.journal.repository.JournalRoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JournalRoleService {

  private final JournalRoleRepository journalRoleRepository;

  public JournalRoleService(JournalRoleRepository journalRoleRepository) {
    this.journalRoleRepository = journalRoleRepository;
  }

  @Transactional(readOnly = true)
  public List<String> getJournalIds(String userId) {
    return journalRoleRepository.findAllJournalIdsByUser(userId);
  }

  @Transactional(readOnly = true)
  public boolean hasPermission(String userId, String journalId, Permission permission) {
    return journalRoleRepository
        .findByUserIdAndJournalId(userId, journalId)
        .map(journalRole -> journalRole.getRole().hasPermission(permission))
        .orElse(false);
  }

  @Transactional
  public void setRole(User user, Journal journal, Role role) {
    Objects.requireNonNull(role, "Role cannot be null. To remove a role, call deleteRole.");

    JournalRole journalRole =
        journalRoleRepository
            .findByUserIdAndJournalId(user.getUserId(), journal.getJournalId())
            .orElse(JournalRole.builder().user(user).journal(journal).role(null).build());

    if (!role.equals(journalRole.getRole())) {
      journalRole.setRole(role);
      journalRoleRepository.save(journalRole);
    }
  }

  @Transactional
  public void deleteRole(String userId, String journalId) {
    journalRoleRepository
        .findByUserIdAndJournalId(userId, journalId)
        .ifPresent(
            role -> {
              journalRoleRepository.delete(role);
            });
  }
}
