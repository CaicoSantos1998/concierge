package github.caicosantos.concierge.exceptions;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Collections;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateRegisterException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handlerDuplicateRegisterException(DuplicateRegisterException e) {
        return ErrorResponse.conflict(e.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handlerDataIntegrityViolationException(DataIntegrityViolationException e) {
        return new ErrorResponse(HttpStatus.CONFLICT.value(), "This a duplicate key that violates unique constraint!", List.of());
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleAccessDenied(AuthorizationDeniedException e) {
        return new ErrorResponse(HttpStatus.FORBIDDEN.value(), "Authorization denied!", List.of());
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handlerAccessDeniedException(AccessDeniedException e) {
        return new ErrorResponse(HttpStatus.FORBIDDEN.value(), "Access Denied!", List.of());
    }

    @ExceptionHandler(SearchCombinationNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handlerSearchCombinationNotFoundException(SearchCombinationNotFoundException e) {
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage(), Collections.emptyList());
    }
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private ErrorResponse handlerEntityNotFoundException(EntityNotFoundException e) {
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Resource not found!", List.of());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handlerMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        String errorMessage = String.format("The parameter '%s' is not a valid UUID", e.getName());
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), errorMessage, List.of());
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handlerBadCredentialsException(BadCredentialsException e) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Authentication failed, the current password is incorrect!", List.of());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handlerErrorNoHandling(RuntimeException e) {
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An unexpected internal error occurred!", List.of());
    }
}
