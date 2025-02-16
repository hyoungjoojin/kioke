package com.kioke.user.exception.discovery;

public class ServiceNotFoundException extends Exception {

  public ServiceNotFoundException(String serviceId) {
    super("Service of ID " + serviceId + " could not be found.");
  }
}
