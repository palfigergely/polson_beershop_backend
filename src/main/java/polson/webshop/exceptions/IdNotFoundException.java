package polson.webshop.exceptions;

import org.springframework.http.HttpStatus;

public class IdNotFoundException extends ApiException {

    public static final String MESSAGE = "ID not found";

    public IdNotFoundException() {
        super(MESSAGE);
        setHttpStatus(HttpStatus.NOT_FOUND);
    }

}
