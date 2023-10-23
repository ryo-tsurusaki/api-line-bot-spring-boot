package api.tsurusaki.presentation.interceptor;

import api.tsurusaki.domain.model.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.Timestamp;
import java.util.Objects;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * {@inheritDoc}
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {

        ObjectError objectError = exception.getBindingResult().getAllErrors().get(0);
        String message = Objects.requireNonNull(objectError.getDefaultMessage());

        ErrorResponse body = ErrorResponse.builder()
                .timestamp(this.getTimeStamp())
                .code(HttpStatus.BAD_REQUEST.value())
                .error(message)
                .uuId(this.getUuIdFromWebRequest(request))
                .build();

        return super.handleExceptionInternal(exception, body, HttpHeaders.EMPTY, HttpStatus.BAD_REQUEST, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            HttpRequestMethodNotSupportedException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {

        ErrorResponse body = ErrorResponse.builder()
                .timestamp(this.getTimeStamp())
                .code(HttpStatus.BAD_REQUEST.value())
                .error(exception.getMessage())
                .uuId(this.getUuIdFromWebRequest(request))
                .build();

        return super.handleExceptionInternal(exception, body, HttpHeaders.EMPTY, HttpStatus.BAD_REQUEST, request);
    }

    /**
     * A single place to customize the response body of all exception types.
     *
     * @param exception exception.
     * @param request   current request.
     * @return ResponseEntity object.
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleExceptions(Exception exception, WebRequest request) {

        ErrorResponse body = ErrorResponse.builder()
                .timestamp(this.getTimeStamp())
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error(exception.getMessage())
                .uuId(this.getUuIdFromWebRequest(request))
                .build();

        return super.handleExceptionInternal(exception, body, HttpHeaders.EMPTY, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    /**
     * get uuId from WebRequest object.
     *
     * @param webRequest WebRequest object.
     * @return uuId.
     */
    private String getUuIdFromWebRequest(WebRequest webRequest) {
        return Objects.requireNonNull(webRequest.getAttribute("uuId", WebRequest.SCOPE_REQUEST)).toString();
    }

    /**
     * get time stamp.
     *
     * @return time stamp string.
     */
    private String getTimeStamp() {
        return new Timestamp(System.currentTimeMillis()).toString();
    }
}
