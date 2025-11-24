package com.springBoot.practice.springTesting.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
public class ErrorResponse {

    private LocalDateTime localDateTime;
    private String message;
    private HttpStatus status;

    public ErrorResponse(){
        this.localDateTime = LocalDateTime.now();
    }

    public ErrorResponse(String message, HttpStatus httpStatus){
        this();
        this.message = message;
        this.status = httpStatus;

    }
}
