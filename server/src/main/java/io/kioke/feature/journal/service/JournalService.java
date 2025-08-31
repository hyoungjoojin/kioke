package io.kioke.feature.journal.service;

import io.kioke.exception.auth.AccessDeniedException;
import io.kioke.exception.collection.CollectionNotFoundException;
import io.kioke.exception.journal.JournalNotFoundException;
import io.kioke.feature.collection.service.CollectionService;
import io.kioke.feature.journal.constant.Role;
import io.kioke.feature.journal.domain.Journal;
import io.kioke.feature.journal.dto.JournalDto;
import io.kioke.feature.journal.dto.JournalRoleDto;
import io.kioke.feature.journal.dto.request.CreateJournalRequestDto;
import io.kioke.feature.journal.dto.request.UpdateJournalRequestDto;
import io.kioke.feature.journal.repository.JournalRepository;
import io.kioke.feature.journal.util.JournalMapper;
import io.kioke.feature.user.domain.User;
import io.kioke.feature.user.dto.UserDto;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JournalService {

  private final JournalRoleService journalRoleService;
  private final CollectionService collectionService;
  private final JournalRepository journalRepository;
  private final JournalMapper journalMapper;

  public JournalService(
      JournalRoleService journalRoleService,
      CollectionService collectionService,
      JournalRepository journalRepository,
      JournalMapper journalMapper) {
    this.journalRoleService = journalRoleService;
    this.collectionService = collectionService;
    this.journalRepository = journalRepository;
    this.journalMapper = journalMapper;
  }

  @Transactional(rollbackFor = Exception.class)
  public JournalDto createJournal(UserDto userDto, CreateJournalRequestDto request)
      throws AccessDeniedException, CollectionNotFoundException {
    User user = User.builder().userId(userDto.userId()).build();

    Journal journal =
        Journal.builder().type(request.type()).author(user).title(request.title()).build();
    journal = journalRepository.save(journal);

    collectionService.addEntryToCollection(user, journal, request.collectionId());
    journalRoleService.setRole(user, journal, Role.AUTHOR);
    return journalMapper.toDto(journal, Role.AUTHOR, Collections.emptyList());
  }

  @Transactional(readOnly = true)
  public JournalDto getJournal(UserDto userDto, String journalId) throws JournalNotFoundException {
    User user = User.builder().userId(userDto.userId()).build();
    Journal journal =
        journalRepository.findById(journalId).orElseThrow(() -> new JournalNotFoundException());

    Role role = journalRoleService.getRole(user, journal);
    if (!journal.getIsPublic() && !role.canRead()) {
      throw new JournalNotFoundException();
    }

    List<JournalRoleDto> collaborators =
        (role.equals(Role.AUTHOR) ? journalRoleService.getRoles(journal) : Collections.emptyList());

    return journalMapper.toDto(journal, role, collaborators);
  }

  @Transactional
  public void updateJournal(UserDto userDto, String journalId, UpdateJournalRequestDto request)
      throws JournalNotFoundException, AccessDeniedException {
    User user = User.builder().userId(userDto.userId()).build();
    Journal journal =
        journalRepository.findById(journalId).orElseThrow(() -> new JournalNotFoundException());

    Role role = journalRoleService.getRole(user, journal);
    if (!role.canEdit()) {
      throw new AccessDeniedException();
    }

    if (request.title() != null) {
      journal.setTitle(request.title());
    }

    if (request.description() != null) {
      journal.setDescription(request.description());
    }

    if (request.isPublic() != null) {
      journal.setIsPublic(request.isPublic());
    }

    journalRepository.save(journal);
  }
}
