package com.kioke.auth.controller;

import com.kioke.auth.constant.Permission;
import com.kioke.auth.dto.response.permission.GetPermissionsResponseBodyDto;
import com.kioke.auth.exception.UserDoesNotExistException;
import com.kioke.auth.model.Journal;
import com.kioke.auth.model.User;
import com.kioke.auth.service.AclService;
import com.kioke.auth.service.JournalService;
import com.kioke.auth.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/permissions/{uid}")
public class PermissionController {
  @Autowired @Lazy AclService aclService;
  @Autowired @Lazy JournalService journalService;
  @Autowired @Lazy UserService userService;

  @GetMapping
  public ResponseEntity<GetPermissionsResponseBodyDto> getJournalPermissions(
      @PathVariable String uid, @RequestParam(required = false) String jid)
      throws UserDoesNotExistException {
    User user = userService.getUserById(uid);

    if (jid == null) {
      GetPermissionsResponseBodyDto responseBodyDto = new GetPermissionsResponseBodyDto();
      responseBodyDto.setCanCreate(true);

      return ResponseEntity.status(HttpStatus.OK).body(responseBodyDto);
    }

    Journal journal = journalService.getJournal(jid);

    List<Permission> permissions = aclService.getJournalPermissions(user, journal);

    return ResponseEntity.status(HttpStatus.OK)
        .body(new GetPermissionsResponseBodyDto(permissions));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void createJournal(@PathVariable String uid, @RequestParam String jid) throws Exception {
    if (jid == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    User user = userService.getUserById(uid);
    Journal journal = journalService.getOrCreateJournal(jid);
    aclService.createAclEntry(user, journal);
  }
}
