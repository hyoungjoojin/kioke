package io.kioke.feature.journal.repository;

import io.kioke.feature.journal.dto.JournalCollectionDto;
import io.kioke.feature.journal.dto.JournalDto;
import io.kioke.feature.journal.dto.JournalPermissionDto;
import io.kioke.feature.user.dto.UserDto;
import java.util.List;
import java.util.Optional;

interface CustomizedJournalRepository {

  public Optional<JournalDto> findByJournalId(String journalId);

  public Optional<JournalCollectionDto> findCollectionByUserAndId(
      UserDto user, String collectionId);

  public List<JournalCollectionDto> findCollectionsByUser(UserDto user);

  public Optional<JournalPermissionDto> findPermissions(UserDto user, JournalDto journal);
}
