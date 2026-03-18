package github.caicosantos.concierge.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateRegisterException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handlerDuplicateRegisterException(DuplicateRegisterException e) {
        return ErrorResponse.conflict(e.getMessage());
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleAccessDenied(AuthorizationDeniedException e) {
        return new ErrorResponse(HttpStatus.FORBIDDEN.value(), "Access denied!", List.of());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handlerErrorNoHandling(RuntimeException e) {
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An unexpected internal error occurred!", List.of());
    }
}
