package com.kioke.auth.dto.response.permission;

import com.kioke.auth.constant.Permission;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetPermissionsResponseBodyDto {
  private Boolean canCreate = false;
  private Boolean canRead = false;
  private Boolean canUpdate = false;
  private Boolean canDelete = false;

  public GetPermissionsResponseBodyDto(List<Permission> permissions) {
    for (Permission permission : permissions) {
      switch (permission) {
        case CREATE:
          canCreate = true;
          break;

        case READ:
          canRead = true;
          break;

        case UPDATE:
          canUpdate = true;
          break;

        case DELETE:
          canDelete = true;
          break;
      }
    }
  }

  public static GetPermissionsResponseBodyDto privileged() {
    return new GetPermissionsResponseBodyDto(true, true, true, true);
  }
}
