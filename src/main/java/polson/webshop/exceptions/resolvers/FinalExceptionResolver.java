package polson.webshop.exceptions.resolvers;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import polson.webshop.exceptions.ApiStatusDTO;

@RestControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
public class FinalExceptionResolver {

  @ExceptionHandler(Exception.class)
  public final ResponseEntity<ApiStatusDTO> handleUnhandledExceptions(Exception ex) {
    return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(ApiStatusDTO.error(ex.getMessage()));
  }
}
