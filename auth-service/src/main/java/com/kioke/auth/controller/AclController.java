package com.kioke.auth.controller;

import com.kioke.auth.dto.CreateJournalRequestBodyDto;
import com.kioke.auth.model.Journal;
import com.kioke.auth.model.User;
import com.kioke.auth.service.AclService;
import com.kioke.auth.service.JournalService;
import com.kioke.auth.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class AclController {
  @Autowired @Lazy AclService aclService;
  @Autowired @Lazy JournalService journalService;
  @Autowired @Lazy UserService userService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void createJournal(@Valid @RequestBody CreateJournalRequestBodyDto requestBody)
      throws Exception {
    User user = userService.getOrCreateUser(requestBody.getUid());
    Journal journal = journalService.getOrCreateJournal(requestBody.getJid());
    aclService.createAclEntry(user, journal);
  }
}
