package polson.webshop.exceptions;

import org.springframework.http.HttpStatus;

public class WrongPasswordFormatException extends ApiException {
    public static final String MESSAGE = "Password must be at least 8 characters long";


    public WrongPasswordFormatException() {
        super(MESSAGE);
        setHttpStatus(HttpStatus.NOT_ACCEPTABLE);
    }
}
