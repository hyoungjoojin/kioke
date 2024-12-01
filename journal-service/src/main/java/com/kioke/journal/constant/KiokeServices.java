package com.kioke.journal.constant;

public enum KiokeServices {
  AUTH_SERVICE("Kioke Auth Service"),
  USER_SERVICE("Kioke User Service"),
  JOURNAL_SERVICE("Kioke Journal Service");

  String serviceId;

  private KiokeServices(String serviceId) {
    this.serviceId = serviceId;
  }

  public String getServiceId() {
    return serviceId;
  }
}
