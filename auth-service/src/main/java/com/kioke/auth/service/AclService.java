package com.kioke.auth.service;

import com.kioke.auth.constant.Permission;
import com.kioke.auth.model.AclEntry;
import com.kioke.auth.model.Journal;
import com.kioke.auth.model.User;
import com.kioke.auth.repository.AclRepository;
import java.util.Arrays;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class AclService {
  @Autowired @Lazy AclRepository aclRepository;

  public void createAclEntry(User user, Journal journal) {
    AclEntry aclEntry =
        aclRepository
            .findByUserAndJournal(user, journal)
            .or(
                () -> {
                  AclEntry newAclEntry = AclEntry.builder().journal(journal).user(user).build();
                  return Optional.of(newAclEntry);
                })
            .get();

    Permission[] permissions = {Permission.READ, Permission.WRITE};
    aclEntry.setPermissions(Arrays.asList(permissions));

    aclRepository.save(aclEntry);
  }
}
