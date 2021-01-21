package polson.webshop.exceptions;

import org.springframework.http.HttpStatus;

public class AuthorisedUserNotFoundException extends ApiException {

    public static final HttpStatus HTTP_STATUS = HttpStatus.UNAUTHORIZED;
    public static final String MESSAGE = "Username or password is incorrect";

    public AuthorisedUserNotFoundException() {
        super(MESSAGE);
        setHttpStatus(HTTP_STATUS);
    }

}
