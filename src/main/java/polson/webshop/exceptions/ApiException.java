package polson.webshop.exceptions;

import org.springframework.http.HttpStatus;

public class ApiException extends RuntimeException {

    private HttpStatus httpStatus;

    public ApiException(String message) { super(message); }

    public HttpStatus getHttpStatus() { return httpStatus; }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}
