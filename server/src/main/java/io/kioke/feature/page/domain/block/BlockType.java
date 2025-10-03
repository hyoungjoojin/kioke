package io.kioke.feature.page.domain.block;

public enum BlockType {
  TEXT_BLOCK(Values.TEXT_BLOCK),
  IMAGE_BLOCK(Values.IMAGE_BLOCK),
  MAP_BLOCK(Values.MAP_BLOCK);

  private String value;

  private BlockType(String value) {
    if (!this.name().equals(value)) {
      throw new IllegalArgumentException("Invalid value for BlockType");
    }

    this.value = value;
  }

  @Override
  public String toString() {
    return value;
  }

  public static class Values {
    public static final String TEXT_BLOCK = "TEXT_BLOCK";
    public static final String IMAGE_BLOCK = "IMAGE_BLOCK";
    public static final String MAP_BLOCK = "MAP_BLOCK";
  }
}
