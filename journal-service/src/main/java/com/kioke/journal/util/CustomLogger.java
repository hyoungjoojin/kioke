package com.kioke.journal.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomLogger {
  private final Logger log;
  private final String format = "(requestId={}) {}";

  public CustomLogger(Class<?> clazz) {
    this.log = LoggerFactory.getLogger(clazz);
  }

  public CustomLogger(String loggerName) {
    this.log = LoggerFactory.getLogger(loggerName);
  }

  public void trace(String id, String message) {
    log.trace(format, id, message);
  }

  public void debug(String id, String message) {
    log.debug(format, id, message);
  }

  public void info(String id, String message) {
    log.info(format, id, message);
  }

  public void warn(String id, String message) {
    log.warn(format, id, message);
  }

  public void error(String id, String message) {
    log.error(format, id, message);
  }
}
