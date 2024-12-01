package com.kioke.journal.exception;

import com.kioke.journal.constant.KiokeServices;

public class ServiceNotFoundException extends Exception {
  private KiokeServices service;

  public ServiceNotFoundException(KiokeServices service) {
    this.service = service;
  }

  @Override
  public String toString() {
    return "Service [" + service.getServiceId() + "] could not be found.";
  }
}
