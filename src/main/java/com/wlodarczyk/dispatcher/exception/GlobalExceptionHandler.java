package com.wlodarczyk.dispatcher.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.time.Instant;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //404
    @ExceptionHandler(ResourceNotFoundException.class)
    public ProblemDetail handleResourceNotFoundException(ResourceNotFoundException ex, HttpServletRequest request){
        return createProblemDetail(
                HttpStatus.NOT_FOUND,
                "Resource Not Found",
                ex.getMessage(),
                ex.getErrorCode(),
                request.getRequestURI()
        );
    }

    //409
    @ExceptionHandler(BusinessException.class)
    public ProblemDetail handleBusinessRuleConflictException(BusinessException ex, HttpServletRequest request){
        return createProblemDetail(
                HttpStatus.CONFLICT,
                "Business Rule Conflict",
                ex.getMessage(),
                ex.getErrorCode(),
                request.getRequestURI()
        );
    }

    //400
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request){
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        return createProblemDetail(
                HttpStatus.BAD_REQUEST,
                "Validation Failed",
                errorMessage,
                ErrorCode.VALIDATION_FAILED,
                request.getRequestURI()
        );
    }

    //400
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ProblemDetail handleReadable(HttpMessageNotReadableException ex, HttpServletRequest request){
        return createProblemDetail(
                HttpStatus.BAD_REQUEST,
                "Malformed Request",
                "Request Body is Invalid",
                ErrorCode.VALIDATION_FAILED,
                request.getRequestURI()
        );
    }

    //500
    @ExceptionHandler(Exception.class)
    public ProblemDetail handleUncaughtExceptions(Exception ex, HttpServletRequest request){
        return createProblemDetail(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Internal Server Error",
                "An unexpected error occured. Please try again",
                ErrorCode.INTERNAL_SERVER_ERROR,
                request.getRequestURI()
        );
    }

    private ProblemDetail createProblemDetail(HttpStatus status, String title, String detail, ErrorCode errorCode, String uri){
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, detail);
        problemDetail.setTitle(title);
        problemDetail.setInstance(URI.create(uri));
        problemDetail.setProperty("code", errorCode.name());
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }
}
