package dev.matheushnt.toolboxapi.exception;

import dev.matheushnt.toolboxapi.dtos.ErrorDetail;
import dev.matheushnt.toolboxapi.enums.ProblemType;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    private static final String BASE_URL = "https://api.toolbox.com/problems/";

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleUnexpectedException(Exception ex, HttpServletRequest request) {
        log.error("Unexpected error while processing the request {}", request.getRequestURI(), ex);

        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        problemDetail.setType(URI.create(BASE_URL + ProblemType.INTERNAL_ERROR.getValue()));
        problemDetail.setTitle(HttpStatus.INTERNAL_SERVER_ERROR.name());
        problemDetail.setDetail("An unexpected error occurred. Please try again later.");
        problemDetail.setInstance(URI.create(request.getRequestURI()));

        return problemDetail;
    }

    @ExceptionHandler(exception = HttpMessageNotReadableException.class)
    public ProblemDetail handleHttpMessageNotReadableException(HttpMessageNotReadableException ex, HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setType(URI.create(BASE_URL + ProblemType.INVALID_DATA.getValue()));
        problemDetail.setTitle(HttpStatus.BAD_REQUEST.name());
        problemDetail.setDetail("The request you submitted is invalid");
        problemDetail.setInstance(URI.create(request.getRequestURI()));

        return problemDetail;
    }

    @ExceptionHandler(exception = MethodArgumentNotValidException.class)
    public ProblemDetail handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        List<ErrorDetail> errors = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(error -> new ErrorDetail(error.getField(), error.getDefaultMessage()))
            .toList();

        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.UNPROCESSABLE_CONTENT);
        problemDetail.setType(URI.create(BASE_URL + ProblemType.VALIDATION_ERROR.getValue()));
        problemDetail.setTitle(HttpStatus.UNPROCESSABLE_CONTENT.name());
        problemDetail.setDetail("One or more fields are invalid");
        problemDetail.setInstance(URI.create(request.getRequestURI()));
        problemDetail.setProperty("errors", errors);

        return problemDetail;
    }

    @ExceptionHandler(exception = { BadCredentialsException.class, UsernameNotFoundException.class })
    public ProblemDetail handleAuthenticationFailure(HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.UNAUTHORIZED);
        problemDetail.setType(URI.create(BASE_URL + ProblemType.BAD_CREDENTIALS.getValue()));
        problemDetail.setTitle(HttpStatus.UNAUTHORIZED.name());
        problemDetail.setDetail("Bad credentials");
        problemDetail.setInstance(URI.create(request.getRequestURI()));

        return problemDetail;
    }

    @ExceptionHandler(exception = ResourceFoundException.class)
    public ProblemDetail handleResourceFoundException(ResourceFoundException ex, HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        problemDetail.setType(URI.create(BASE_URL + ProblemType.CONFLICT.getValue()));
        problemDetail.setTitle(HttpStatus.CONFLICT.name());
        problemDetail.setDetail(ex.getMessage());
        problemDetail.setInstance(URI.create(request.getRequestURI()));

        return problemDetail;
    }

}
