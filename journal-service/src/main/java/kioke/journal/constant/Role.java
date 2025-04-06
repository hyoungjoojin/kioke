package kioke.journal.constant;

import java.util.Set;

public enum Role {
  AUTHOR(Set.of(Permission.READ, Permission.WRITE, Permission.DELETE, Permission.SHARE)),
  EDITOR(Set.of(Permission.READ, Permission.WRITE)),
  READER(Set.of(Permission.READ));

  private final Set<Permission> permissions;

  private Role(Set<Permission> permissions) {
    this.permissions = permissions;
  }

  public boolean hasPermission(Permission permission) {
    return permissions.contains(permission);
  }
}
