package com.kioke.journal.constant;

public enum KiokeServices {
  AUTH_SERVICE("auth"),
  USER_SERVICE("user"),
  JOURNAL_SERVICE("journal");

  String serviceId;

  private KiokeServices(String serviceId) {
    this.serviceId = serviceId;
  }

  public String getServiceId() {
    return serviceId;
  }
}
