package io.kioke.feature.journal.service;

import io.kioke.feature.journal.constant.Role;
import io.kioke.feature.journal.domain.Journal;
import io.kioke.feature.journal.domain.JournalRole;
import io.kioke.feature.journal.dto.JournalRoleDto;
import io.kioke.feature.journal.repository.JournalRoleRepository;
import io.kioke.feature.user.domain.User;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JournalRoleService {

  private final JournalRoleRepository journalRoleRepository;

  public JournalRoleService(JournalRoleRepository journalRoleRepository) {
    this.journalRoleRepository = journalRoleRepository;
  }

  @Transactional(readOnly = true)
  public Role getRole(User user, Journal journal) {
    return journalRoleRepository
        .findByUserAndJournal(user, journal)
        .map(role -> role.getRole())
        .orElse(Role.NONE);
  }

  @Transactional(readOnly = true)
  public List<JournalRoleDto> getRoles(Journal journal) {
    return journalRoleRepository.findByJournal(journal);
  }

  @Transactional
  public void setRole(User user, Journal journal, Role role) {
    JournalRole journalRole =
        journalRoleRepository
            .findByUserAndJournal(user, journal)
            .orElse(JournalRole.of(user, journal, role));

    journalRole.setRole(role);
    journalRoleRepository.save(journalRole);
  }
}
