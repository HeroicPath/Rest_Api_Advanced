package com.epam.esm.Rest_Api_Advanced.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter @Setter
@AllArgsConstructor
public class LocalException extends RuntimeException{

    private String message;
    private HttpStatus httpStatus;
}
