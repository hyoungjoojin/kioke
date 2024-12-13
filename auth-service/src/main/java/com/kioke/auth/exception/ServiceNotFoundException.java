package com.kioke.auth.exception;

import com.kioke.auth.constant.KiokeServices;

public class ServiceNotFoundException extends Exception {

  public ServiceNotFoundException(KiokeServices service) {
    super("Service of ID " + service.getServiceId() + " could not be found.");
  }
}
