package polson.webshop.exceptions;

import org.springframework.http.HttpStatus;

public class ForbiddenRequestException extends ApiException {

    public static final HttpStatus HTTP_STATUS = HttpStatus.UNAUTHORIZED;
    public static final String MESSAGE = "Unauthorized request";

    public ForbiddenRequestException() {
        super(MESSAGE);
        setHttpStatus(HTTP_STATUS);
    }
}
