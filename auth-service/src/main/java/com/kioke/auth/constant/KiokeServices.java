package com.kioke.auth.constant;

public enum KiokeServices {
  USER_SERVICE("user");

  private String serviceId;

  private KiokeServices(String serviceId) {
    this.serviceId = serviceId;
  }

  public String getServiceId() {
    return serviceId;
  }
}
