package polson.webshop.exceptions;

import org.springframework.http.HttpStatus;

public class UsernameAlreadyTakenException extends ApiException {
  public static final String MESSAGE = "Username is already taken";

  public UsernameAlreadyTakenException() {
    super(MESSAGE);
    setHttpStatus(HttpStatus.CONFLICT);
  }
}
