package kioke.journal.service;

import java.util.Optional;
import kioke.journal.constant.Permission;
import kioke.journal.constant.Role;
import kioke.journal.model.Journal;
import kioke.journal.model.JournalRole;
import kioke.journal.model.User;
import kioke.journal.repository.JournalRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JournalRoleService {

  @Autowired private JournalRoleRepository journalRoleRepository;

  public boolean hasPermission(User user, Journal journal, Permission permission) {
    return journalRoleRepository
        .findByUserAndJournal(user, journal)
        .map(journalRole -> journalRole.getRole().hasPermission(permission))
        .orElse(false);
  }

  public void setRole(User user, Journal journal, Role role) {
    JournalRole journalRole =
        journalRoleRepository
            .findByUserAndJournal(user, journal)
            .orElse(JournalRole.builder().user(user).journal(journal).role(null).build());

    if (role.equals(journalRole.getRole())) {
      return;
    }

    journalRole.setRole(role);
    journalRoleRepository.save(journalRole);
  }

  public void deleteRole(User user, Journal journal) {
    Optional<JournalRole> journalRole = journalRoleRepository.findByUserAndJournal(user, journal);

    if (journalRole.isPresent()) {
      journalRoleRepository.deleteById(journalRole.get().getId());
    }
  }
}
