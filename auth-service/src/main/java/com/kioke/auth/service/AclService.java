package com.kioke.auth.service;

import com.kioke.auth.constant.Permission;
import com.kioke.auth.model.AclEntry;
import com.kioke.auth.model.Journal;
import com.kioke.auth.model.User;
import com.kioke.auth.repository.AclRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class AclService {
  @Autowired @Lazy AclRepository aclRepository;

  public List<Permission> getJournalPermissions(User user, Journal journal) {
    Optional<AclEntry> optionalAclEntry = aclRepository.findByUserAndJournal(user, journal);
    if (optionalAclEntry.isEmpty()) {
      return new ArrayList<>();
    }

    return optionalAclEntry.get().getPermissions();
  }

  public void createAclEntry(User user, Journal journal) {
    AclEntry aclEntry =
        aclRepository
            .findByUserAndJournal(user, journal)
            .or(
                () -> {
                  AclEntry newAclEntry = AclEntry.builder().user(user).journal(journal).build();
                  return Optional.of(newAclEntry);
                })
            .get();

    Permission[] permissions = {Permission.READ, Permission.UPDATE, Permission.DELETE};
    aclEntry.setPermissions(Arrays.asList(permissions));

    aclRepository.save(aclEntry);
  }
}
