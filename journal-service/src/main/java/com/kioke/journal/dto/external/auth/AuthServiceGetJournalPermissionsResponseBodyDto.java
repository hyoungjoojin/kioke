package com.kioke.journal.dto.external.auth;

import com.kioke.journal.constant.Permission;
import java.util.List;
import lombok.Data;

@Data
public class AuthServiceGetJournalPermissionsResponseBodyDto {
  private final List<Permission> permissions;
}
