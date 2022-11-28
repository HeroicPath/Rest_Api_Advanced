package com.epam.esm.RestApiAdvanced.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class LocalException extends RuntimeException{

    private String message;
    private HttpStatus httpStatus;
}
