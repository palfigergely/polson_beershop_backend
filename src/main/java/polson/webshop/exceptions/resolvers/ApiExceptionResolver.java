package polson.webshop.exceptions.resolvers;

import io.jsonwebtoken.io.DeserializationException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.JDBCException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import polson.webshop.exceptions.ApiException;
import polson.webshop.exceptions.ApiStatusDTO;
import polson.webshop.exceptions.UnauthorizedRequestException;

@Slf4j
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ApiExceptionResolver {

    @ExceptionHandler(ApiException.class)
    public final ResponseEntity<ApiStatusDTO> handleApiException(ApiException apiException) {
        return ResponseEntity
                .status(apiException.getHttpStatus())
                .body(ApiStatusDTO.error(apiException.getMessage()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public final ResponseEntity<ApiStatusDTO> handleEmptyBodyException(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiStatusDTO.error("You should provide data in body"));
    }

    @ExceptionHandler(MissingPathVariableException.class)
    public final ResponseEntity<ApiStatusDTO> handleMissingPathVariableException(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiStatusDTO.error("You should provide data in path"));
    }

    @ExceptionHandler({
            DataAccessException.class,
            JDBCException.class
    })
    public final ResponseEntity<ApiStatusDTO> handleDbExceptions(Exception ex) {
        log.error("Database error", ex);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiStatusDTO.error("Database error"));
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public final ResponseEntity<ApiStatusDTO> handleNoHandlerFoundException(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ApiStatusDTO.error("No such resource."));
    }

    @ExceptionHandler({InsufficientAuthenticationException.class,
            UnauthorizedRequestException.class,
            DeserializationException.class})
    public final ResponseEntity<ApiStatusDTO> handleAuthExceptions(Exception ex) {
        return ResponseEntity
                .status(UnauthorizedRequestException.HTTP_STATUS)
                .body(ApiStatusDTO.error(UnauthorizedRequestException.MESSAGE));
    }
}
