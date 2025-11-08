package io.kioke.feature.block.dto.operation;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    visible = true,
    include = JsonTypeInfo.As.EXISTING_PROPERTY,
    property = "op")
@JsonSubTypes({
  @Type(value = UpdateBlockOperation.class, name = BlockOperationType.Values.UPDATE),
  @Type(value = DeleteBlockOperation.class, name = BlockOperationType.Values.DELETE)
})
public interface BlockOperation {

  public String blockId();

  public String pageId();
}
