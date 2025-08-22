package io.kioke.exception.collection;

import io.kioke.constant.ErrorCode;
import io.kioke.exception.KiokeException;

public class CollectionNotFoundException extends KiokeException {

  @Override
  public ErrorCode code() {
    return ErrorCode.COLLECTION_NOT_FOUND;
  }
}
