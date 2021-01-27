package polson.webshop.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiStatusDTO {

  private String status;
  private String message;

  public static ApiStatusDTO ok(String message) {
    return new ApiStatusDTO("ok", message);
  }

  public static ApiStatusDTO error(String message) {
    return new ApiStatusDTO("error", message);
  }
}
