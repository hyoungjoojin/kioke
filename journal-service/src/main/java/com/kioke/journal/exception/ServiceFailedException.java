package com.kioke.journal.exception;

import com.kioke.journal.constant.KiokeServices;

public class ServiceFailedException extends Exception {
  private KiokeServices service;
  private String details;

  public ServiceFailedException(KiokeServices service, String details) {
    this.service = service;
    this.details = details;
  }

  @Override
  public String toString() {
    return "Service [" + service.getServiceId() + "] has failed due to exception " + details;
  }
}
