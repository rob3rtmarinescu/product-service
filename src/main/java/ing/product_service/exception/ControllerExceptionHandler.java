package ing.product_service.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

    /*As an alternative to this Controller exception handler, AOP could be used for handling exceptions (and avoiding code duplication). */

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
        String message = "Resource not found: " + ex.getMessage();
        log.error(message, ex);

        return buildResponse(message, true, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatch(final MethodArgumentTypeMismatchException ex) {
        log.error("MethodArgumentTypeMismatchException occurred!", ex);
        return buildResponse(ex.getValue() + " is an invalid value for parameter " + ex.getName(), true, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleMethodNotSupported(final HttpRequestMethodNotSupportedException ex,
                                                                  final HttpServletRequest request) {
        final String message = request.getRequestURI() + " cannot be called with " + request.getMethod() + "!";
        log.error("HttpRequestMethodNotSupportedException occurred: " + message, ex);
        return buildResponse(message, true, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAppException(final Exception e) {
        log.error("Handling encountered exception...", e);
        final HttpStatus status;
        final String message = e.getMessage() == null ? e.getClass().getName() : e.getMessage();
        if (e.getClass().isAnnotationPresent(ResponseStatus.class)) {
            status = e.getClass().getAnnotation(ResponseStatus.class).value();
        } else {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return buildResponse(message, true, status);
    }

    private ResponseEntity<ErrorResponse> buildResponse(final String message,
                                                        final boolean hideableMessage,
                                                        final HttpStatus status) {
        final String errorMessage;
        if (hideableMessage) {
            errorMessage = null;
        } else {
            errorMessage = message;
        }
        final ErrorResponse errorResponse = new ErrorResponse(errorMessage, status.value());
        log.info("Returning error response is {}.", errorResponse);
        return new ResponseEntity<>(errorResponse, status);
    }
}
