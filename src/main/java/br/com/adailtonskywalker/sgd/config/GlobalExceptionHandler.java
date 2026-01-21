package br.com.adailtonskywalker.sgd.config;

import br.com.adailtonskywalker.sgd.dto.BadRequestData;
import br.com.adailtonskywalker.sgd.dto.BadRequestResponse;
import br.com.adailtonskywalker.sgd.dto.ErrorRequestResponse;
import br.com.adailtonskywalker.sgd.exception.EntityExistsException;
import br.com.adailtonskywalker.sgd.exception.EntityNotFoundException;
import br.com.adailtonskywalker.sgd.exception.UnauthorizedActionException;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;
import java.util.UUID;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorRequestResponse> handleException(Exception exception) {
        log.error(exception.getMessage(), exception);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ErrorRequestResponse.builder()
                    .description("An error occurred, please contact the administrators")
                    .status((short) HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build()
            );
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorRequestResponse> handleException(EntityNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(ErrorRequestResponse.builder()
                    .description(exception.getMessage())
                    .status(exception.getStatus())
                    .build()
            );
    }

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<ErrorRequestResponse> handleException(EntityExistsException exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ErrorRequestResponse.builder()
                    .description(exception.getMessage())
                    .status(exception.getStatus())
                    .build()
            );
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorRequestResponse> handleException(UsernameNotFoundException ignored) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ErrorRequestResponse.builder()
                    .description("User not found")
                    .status((short) HttpStatus.NOT_FOUND.value())
                    .build()
            );
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorRequestResponse> handleException(BadCredentialsException exception) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ErrorRequestResponse.builder()
                        .description(exception.getMessage())
                        .status((short) HttpStatus.UNAUTHORIZED.value())
                        .build()
                );
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorRequestResponse> handleException(AccessDeniedException exception) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ErrorRequestResponse.builder()
                        .description(exception.getMessage())
                        .status((short) HttpStatus.UNAUTHORIZED.value())
                        .build()
                );
    }

    @ExceptionHandler(UnauthorizedActionException.class)
    public ResponseEntity<ErrorRequestResponse> handleException(UnauthorizedActionException exception) {
        return ResponseEntity.status(exception.getStatus())
                .body(ErrorRequestResponse.builder()
                        .description(exception.getMessage())
                        .status(exception.getStatus())
                        .build()
                );
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ErrorRequestResponse> handleException(ExpiredJwtException exception) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ErrorRequestResponse.builder()
                        .description(exception.getMessage())
                        .status((short) HttpStatus.UNAUTHORIZED.value())
                        .build()
                );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BadRequestResponse> handleException(MethodArgumentNotValidException exception) {
        List<BadRequestData> errors = exception.getAllErrors()
                .stream()
                .map(error -> {
                    String field = (error instanceof FieldError fe) ? fe.getField() : error.getObjectName();

                    return BadRequestData.builder()
                            .field(field)
                            .message(error.getDefaultMessage())
                            .build();
                })
                .toList();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(BadRequestResponse.builder()
                    .description("Validation failed")
                    .errors(errors)
                    .build()
            );
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> handleUUIDError(MethodArgumentTypeMismatchException ex) {
        if (ex.getRequiredType() == UUID.class) {
            return ResponseEntity.badRequest().body(
                    ErrorRequestResponse.builder()
                            .status((short) HttpStatus.BAD_REQUEST.value())
                            .description("Invalid UUID")
                            .build()
            );
        }
        throw ex;
    }
}
