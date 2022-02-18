package com.learnigPath.rest.webservices.restfulwebservice.exception;

import com.learnigPath.rest.webservices.restfulwebservice.user.PostNotFoundException;
import com.learnigPath.rest.webservices.restfulwebservice.user.UserNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
@RestController
public class CustomisedCentralExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllException(Exception ex, WebRequest request) {

               ExceptionResponseFormat exceptionFormat = new ExceptionResponseFormat(new Date(), ex.getMessage(),request.getDescription(false));

           return  new ResponseEntity<>(exceptionFormat, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex, WebRequest request) {

        ExceptionResponseFormat exceptionFormat = new ExceptionResponseFormat(new Date(), ex.getMessage(),request.getDescription(false));

        return  new ResponseEntity<>(exceptionFormat, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PostNotFoundException.class)
    public final ResponseEntity<Object> handlePostNotFoundException(PostNotFoundException ex, WebRequest request) {

        ExceptionResponseFormat exceptionFormat = new ExceptionResponseFormat(new Date(), ex.getMessage(),request.getDescription(false));

        return  new ResponseEntity<>(exceptionFormat, HttpStatus.NOT_FOUND);
    }
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        ExceptionResponseFormat exceptionFormat = new ExceptionResponseFormat(new Date(), "validation failed",ex.getBindingResult().getFieldError().toString());

        return  new ResponseEntity<>(exceptionFormat, HttpStatus.BAD_REQUEST);
    }

}
