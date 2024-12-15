package com.kioke.journal.dto.external.auth;

import com.kioke.journal.constant.Permission;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class GetPermissionsResponseBodyDto {
  private final Boolean canCreate;
  private final Boolean canRead;
  private final Boolean canUpdate;
  private final Boolean canDelete;

  public List<Permission> intoPermissionsList() {
    List<Permission> permissionsList = new ArrayList<Permission>();

    if (canCreate) {
      permissionsList.add(Permission.CREATE);
    }

    if (canRead) {
      permissionsList.add(Permission.READ);
    }

    if (canUpdate) {
      permissionsList.add(Permission.UPDATE);
    }

    if (canDelete) {
      permissionsList.add(Permission.DELETE);
    }

    return permissionsList;
  }
}
