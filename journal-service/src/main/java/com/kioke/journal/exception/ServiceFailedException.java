package com.kioke.journal.exception;

public class ServiceFailedException extends Exception {
  private String serviceId;
  private String details;

  public ServiceFailedException(String serviceId, String details) {
    this.serviceId = serviceId;
    this.details = details;
  }

  @Override
  public String toString() {
    return "Service of ID " + serviceId + " has failed due to exception " + details;
  }
}
