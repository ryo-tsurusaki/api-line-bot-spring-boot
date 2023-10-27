package jp.tsurusaki.application.interceptor;

import jp.tsurusaki.application.resource.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.Timestamp;
import java.util.Objects;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

  /**
   * A single place to customize the response body of all exception types.
   *
   * @param exception exception.
   * @param request   current request.
   * @return ResponseEntity object.
   */
  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ResponseEntity<ErrorResponse> handleExceptions(
      Exception exception, WebRequest request) {

    String uuId = this.getUuIdFromWebRequest(request);

    this.outputErrorMessage(uuId, exception.getMessage());

    return ResponseEntity.internalServerError()
        .body(new ErrorResponse(
            this.getTimeStamp(),
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            uuId,
            exception.getMessage()));
  }

  /**
   * get uuId from WebRequest object.
   *
   * @param webRequest WebRequest object.
   * @return uuId.
   */
  private String getUuIdFromWebRequest(WebRequest webRequest) {
    return Objects.requireNonNull(
        webRequest.getAttribute("uuId", WebRequest.SCOPE_REQUEST))
        .toString();
  }

  /**
   * get time stamp.
   *
   * @return time stamp string.
   */
  private String getTimeStamp() {

    return new Timestamp(System.currentTimeMillis()).toString();
  }

  /**
   * output error message.
   *
   * @param uuId    UUID
   * @param message message
   */
  private void outputErrorMessage(String uuId, String message) {

    log.error("uuId=".concat(uuId).concat(", message").concat(message));
  }
}
