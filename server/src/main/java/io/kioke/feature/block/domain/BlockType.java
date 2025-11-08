package io.kioke.feature.block.domain;

public enum BlockType {
  TEXT_BLOCK(Values.TEXT_BLOCK),
  GALLERY_BLOCK(Values.GALLERY_BLOCK),
  IMAGE_BLOCK(Values.IMAGE_BLOCK),
  MAP_BLOCK(Values.MAP_BLOCK),
  MARKER_BLOCK(Values.MARKER_BLOCK);

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
    public static final String GALLERY_BLOCK = "GALLERY_BLOCK";
    public static final String IMAGE_BLOCK = "IMAGE_BLOCK";
    public static final String MAP_BLOCK = "MAP_BLOCK";
    public static final String MARKER_BLOCK = "MARKER_BLOCK";
  }
}
