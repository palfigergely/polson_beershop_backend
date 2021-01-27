package polson.webshop.exceptions;

import org.springframework.http.HttpStatus;

public class ParameterMissingException extends ApiException {

  public static final HttpStatus HTTP_STATUS = HttpStatus.BAD_REQUEST;

  public ParameterMissingException(String... missingParams) {
    super(missingParamsMessage(missingParams));
    setHttpStatus(HTTP_STATUS);
  }

  public static String missingParamsMessage(String[] params) {
    String missingParams = String.join(", ", params);
    StringBuilder missingParamsMessage = new StringBuilder(missingParams)
            .replace(0, 1, missingParams.substring(0, 1).toUpperCase())
            .append(params.length == 1 ? " is " : " are ")
            .append("required.");
    if (params.length > 1) {
      int lastComaIndex = missingParams.lastIndexOf(',');
      missingParamsMessage.replace(lastComaIndex, lastComaIndex + 1, " and");
    }
    return missingParamsMessage.toString();
  }
}
