package com.kioke.auth.exception;

import com.kioke.auth.constant.KiokeServices;
import org.springframework.http.HttpStatusCode;

public class ServiceFailedException extends RuntimeException {

  public ServiceFailedException(KiokeServices service, HttpStatusCode statusCode, String body) {
    super(
        "Service of ID "
            + service.getServiceId()
            + " failed with status code "
            + statusCode
            + " and body "
            + body);
  }
}
