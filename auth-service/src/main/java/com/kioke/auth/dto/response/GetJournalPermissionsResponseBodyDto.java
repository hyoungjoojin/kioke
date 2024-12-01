package com.kioke.auth.dto.response;

import com.kioke.auth.constant.Permission;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetJournalPermissionsResponseBodyDto {
  private final List<Permission> permissions;
}
