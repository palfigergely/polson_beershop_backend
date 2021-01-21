package polson.webshop.exceptions;

import org.springframework.http.HttpStatus;

public class BrewerynameAlreadyTakenException extends ApiException {
    public static final String MESSAGE = "Breweryname is already taken";

    public BrewerynameAlreadyTakenException() {
        super(MESSAGE);
        setHttpStatus(HttpStatus.CONFLICT);
    }
}
