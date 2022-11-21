package com.epam.esm.Rest_Api_Advanced.exception;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> methodArgumentTypeMismatchExceptionHandler(MethodArgumentTypeMismatchException exception) {
        return new ResponseEntity<>(exception.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleExceptionInternal(ex, ex.getAllErrors().get(0).getDefaultMessage(), headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleExceptionInternal(ex, "You provided wrong data type for POST request", headers, status, request);
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<Object> duplicateKeyExceptionHandler(DuplicateKeyException ex, HttpHeaders headers, HttpStatus status, WebRequest request){
        return new ResponseEntity<>(ex.getMessage(), status);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<Object> generalThrowableHandler(Throwable ex, HttpStatus status){
        return new ResponseEntity<>(ex.getLocalizedMessage(), status);
    }
}
