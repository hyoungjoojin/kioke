package kioke.user.exception.discovery;

import kioke.commons.exception.KiokeException;
import org.springframework.http.HttpStatus;

public class ServiceNotFoundException extends KiokeException {

  @Override
  public HttpStatus getStatus() {
    return HttpStatus.NOT_FOUND;
  }

  @Override
  protected String getTitle() {
    return "Service could not be found.";
  }
}
