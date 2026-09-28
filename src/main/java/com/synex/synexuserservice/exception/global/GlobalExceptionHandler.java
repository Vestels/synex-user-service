package com.synex.synexuserservice.exception.global;

import com.synex.synexuserservice.dto.api.ApiErrorDto;
import com.synex.synexuserservice.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiErrorDto> handleHttpMessageNotReadable(HttpMessageNotReadableException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiErrorDto(
                        "BAD_REQUEST",
                        exception.getMessage()
                ));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorDto> handleMethodArgumentNotValid(MethodArgumentNotValidException exception) {
        String message = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .filter(Objects::nonNull)
                .findFirst()
                .orElse("Validation failed.");

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiErrorDto(
                        "BAD_REQUEST",
                        message
                ));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiErrorDto> handleIllegalArgument(IllegalArgumentException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiErrorDto(
                        "BAD_REQUEST",
                        exception.getMessage()
                ));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiErrorDto> handleBadRequest(BadRequestException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiErrorDto(
                        "BAD_REQUEST",
                        exception.getMessage()
                ));
    }

    @ExceptionHandler(Auth0UserNotFoundException.class)
    public ResponseEntity<ApiErrorDto> handleIllegalArgument(Auth0UserNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.GONE)
                .body(new ApiErrorDto(
                        "NOT_FOUND",
                        exception.getMessage()
                ));
    }

    @ExceptionHandler(AccountLinkRequiredException.class)
    public ResponseEntity<ApiErrorDto> handleAccountLinkRequired(AccountLinkRequiredException exception) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ApiErrorDto(
                        "ACCOUNT_LINK_REQUIRED",
                        exception.getMessage()
                ));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiErrorDto> handleUserNotFound(UserNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ApiErrorDto(
                        "USER_NOT_FOUND",
                        exception.getMessage()
                ));
    }

    @ExceptionHandler(UserProfileNotFoundException.class)
    public ResponseEntity<ApiErrorDto> handleUserProfileNotFound(UserProfileNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ApiErrorDto(
                        "USER_PROFILE_NOT_FOUND",
                        exception.getMessage()
                ));
    }

    @ExceptionHandler(UserPreferencesNotFoundException.class)
    public ResponseEntity<ApiErrorDto> handleUserPreferencesNotFound(UserPreferencesNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ApiErrorDto(
                        "USER_PREFERENCES_NOT_FOUND",
                        exception.getMessage()
                ));
    }

    @ExceptionHandler(UserIdentityNotFoundException.class)
    public ResponseEntity<ApiErrorDto> handleUserIdentityNotFound(UserIdentityNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ApiErrorDto(
                        "USER_IDENTITY_NOT_FOUND",
                        exception.getMessage()
                ));
    }
}
