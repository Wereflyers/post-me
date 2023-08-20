package com.example.postme.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.Arrays;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleNullException(final NullPointerException e) {
        log.info("NullPointerException " + Arrays.toString(e.getStackTrace()));
        return new ApiError("NOT_FOUND", "The required object was not found.", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError handleException(final Exception e) {
        log.info("Exception " + Arrays.toString(e.getStackTrace()));
        return new ApiError("BAD_REQUEST", "Incorrectly made request", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleValidationException(final ConstraintViolationException e) {
        log.info("Validation exception " + Arrays.toString(e.getStackTrace()));
        return new ApiError("BAD_REQUEST", "Incorrectly made request", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleValidationException(final MissingServletRequestParameterException e) {
        log.info("Validation exception " + Arrays.toString(e.getStackTrace()));
        return new ApiError("BAD_REQUEST", "Incorrectly made request", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleValidationException(final MethodArgumentNotValidException e) {
        log.info("Validation exception " + Arrays.toString(e.getStackTrace()));
        return new ApiError("BAD_REQUEST", "Incorrectly made request", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleValidationException(final HttpMessageNotReadableException e) {
        log.info("Validation exception " + Arrays.toString(e.getStackTrace()));
        return new ApiError("BAD_REQUEST", "Incorrectly made request", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleWrongRegException(final WrongRegException e) {
        log.info("WrongRegException " + Arrays.toString(e.getStackTrace()));
        return new ApiError("BAD_REQUEST", "For the requested operation the conditions are not met.", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiError handleUnauthorizedException(final UnauthorizedException e) {
        log.info("UnauthorizedException " + Arrays.toString(e.getStackTrace()));
        return new ApiError("UNAUTHORIZED", "User has not authorized.", e.getMessage());
    }
}
