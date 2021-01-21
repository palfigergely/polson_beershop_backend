package polson.webshop.exceptions;

import org.springframework.http.HttpStatus;

public class UnauthorizedRequestException extends ApiException {

    public static final HttpStatus HTTP_STATUS = HttpStatus.UNAUTHORIZED;
    public static final String MESSAGE = "Unauthorized request";

    public UnauthorizedRequestException() {
        super(MESSAGE);
        setHttpStatus(HTTP_STATUS);
    }
}
