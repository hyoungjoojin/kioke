package io.kioke.feature.block.dto.operation;

public enum BlockOperationType {
  UPDATE(Values.UPDATE),
  DELETE(Values.DELETE);

  private String value;

  private BlockOperationType(String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return value;
  }

  public static final class Values {

    public static final String UPDATE = "UPDATE";
    public static final String DELETE = "DELETE";
  }
}
