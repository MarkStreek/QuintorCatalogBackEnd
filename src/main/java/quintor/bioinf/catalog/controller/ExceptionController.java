package quintor.bioinf.catalog.controller;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import quintor.bioinf.catalog.model.ReturnMessage;

import java.nio.file.AccessDeniedException;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * This class is responsible for handling exceptions that are thrown in the controller layer.
 * It catches exceptions and returns a message with the exception message and the request description.
 * The exceptions that are caught are:
 * - NullPointerException
 * - IllegalArgumentException
 * - IllegalStateException
 * - ConstraintViolationException
 * - NoSuchElementException
 * - MethodArgumentNotValidException
 * - BadCredentialsException
 * - ExpiredJwtException
 * - JwtException
 */
@RestControllerAdvice
public class ExceptionController {

    private static final Logger log = LoggerFactory.getLogger(ExceptionController.class);

    @ExceptionHandler(value = {NullPointerException.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ReturnMessage nullPointerException(Exception ex, WebRequest request) {
        log.error("Null pointer exception: {}", ex.getMessage());
        return new ReturnMessage(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(true));
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ReturnMessage illegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        log.error("Illegal argument exception: {}", ex.getMessage());
        return new ReturnMessage(
                HttpStatus.BAD_REQUEST.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(true));
    }

    @ExceptionHandler(value = {IllegalStateException.class})
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public ReturnMessage illegalStateException(IllegalStateException ex, WebRequest request) {
        log.error("Illegal state exception: {}", ex.getMessage());
        return new ReturnMessage(
                HttpStatus.CONFLICT.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(true));
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ReturnMessage legalStateException(IllegalStateException ex, WebRequest request) {
        log.error("Constraint violation exception: {}", ex.getMessage());
        return new ReturnMessage(
                HttpStatus.BAD_REQUEST.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(true));
    }

    @ExceptionHandler(value = {NoSuchElementException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ReturnMessage noSuchElementException(NoSuchElementException ex, WebRequest request) {
        log.error("No such element exception: {}", ex.getMessage());
        return new ReturnMessage(
                HttpStatus.NOT_FOUND.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(true));
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ReturnMessage methodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
        String error = Objects.requireNonNull(ex.getBindingResult().getFieldError()).getDefaultMessage();
        log.error("There is something wrong with the validation of the argument: {}", ex.getMessage());
        return new ReturnMessage(
                HttpStatus.BAD_REQUEST.value(),
                new Date(),
                error,
                request.getDescription(true));
    }

    @ExceptionHandler(value = {BadCredentialsException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ReturnMessage badCredentialsException(BadCredentialsException ex, WebRequest request) {
        log.error("Bad credentials exception: {}", ex.getMessage());
        return new ReturnMessage(
                HttpStatus.BAD_REQUEST.value(),
                new Date(),
                "Incorrecte login gegevens: " + ex.getMessage(),
                request.getDescription(true));
    }

    @ExceptionHandler(value = {ExpiredJwtException.class})
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public ReturnMessage expiredJwtException(ExpiredJwtException ex, WebRequest request) {
        log.error("Expired JWT exception: {}", ex.getMessage());
        return new ReturnMessage(
                HttpStatus.FORBIDDEN.value(),
                new Date(),
                "Authenticatie Token is verlopen: " + ex.getMessage(),
                request.getDescription(true));
    }

    @ExceptionHandler(value = {JwtException.class})
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public ReturnMessage jwtException(JwtException ex, WebRequest request) {
        log.error("JWT exception: {}", ex.getMessage());
        return new ReturnMessage(
                HttpStatus.FORBIDDEN.value(),
                new Date(),
                "Er is iets mis met JWT token: " + ex.getMessage(),
                request.getDescription(true));
    }

    @ExceptionHandler(value = {AccessDeniedException.class})
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public ReturnMessage accessDeniedException(AccessDeniedException ex, WebRequest request) {
        log.error("Access denied exception: {}", ex.getMessage());
        return new ReturnMessage(
                HttpStatus.FORBIDDEN.value(),
                new Date(),
                "Toegang geweigerd: " + ex.getMessage(),
                request.getDescription(true));
    }
}
