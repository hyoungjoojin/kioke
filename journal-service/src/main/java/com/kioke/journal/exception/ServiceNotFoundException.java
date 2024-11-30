package com.kioke.journal.exception;

public class ServiceNotFoundException extends Exception {
  private String serviceId;

  public ServiceNotFoundException(String serviceId) {
    this.serviceId = serviceId;
  }

  @Override
  public String toString() {
    return "Service of ID " + serviceId + " could not be found.";
  }
}
