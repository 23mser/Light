package com.moskovets.light.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleUserNotFoundException(final UserNotFoundException e) {
        log.error("Error UserNotFoundException {}", e.getMessage());

        return ErrorResponse.builder()
                .status(HttpStatus.NOT_FOUND.name())
                .reason("Пользователь не найден.")
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationException(final ValidationException e) {
        log.error("Error ValidationException {}", e.getMessage());

        return ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.name())
                .reason("Ошибка валидации.")
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }


    @ExceptionHandler(AuthException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleAuthException(final AuthException e) {
        log.error("Error AuthException {}", e.getMessage());

        return ErrorResponse.builder()
                .status(HttpStatus.FORBIDDEN.name())
                .reason("Ошибка авторизации.")
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(RequestNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleRequestNotFoundException(final RequestNotFoundException e) {
        log.error("Error RequestNotFoundException {}", e.getMessage());

        return ErrorResponse.builder()
                .status(HttpStatus.NOT_FOUND.name())
                .reason("Запрос не найден.")
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleException(final Exception e) {
        log.warn("Error Exception {}", e.getMessage());

        ErrorResponse response = ErrorResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
                .reason("Непредвиденная ошибка.")
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(value = IncorrectRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handlerIncorrectRequestException(final IncorrectRequestException e) {
        log.warn("Error IncorrectRequestException {}", e.getMessage());

        ErrorResponse response = ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.name())
                .reason("Некорректный запрос.")
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
