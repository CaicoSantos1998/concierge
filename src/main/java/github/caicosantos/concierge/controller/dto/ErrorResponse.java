package github.caicosantos.concierge.controller.dto;

import org.springframework.http.HttpStatus;

import java.util.List;

public record ErrorResponse(int status, String msg, List<ErrorField> fieldErrors) {
    public static ErrorResponse responseStandard(String msg) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), msg, List.of());
    }

    public static ErrorResponse conflict(String msg) {
        return new ErrorResponse(HttpStatus.CONFLICT.value(), msg, List.of());
    }
}
