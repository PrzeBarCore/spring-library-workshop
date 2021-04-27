package com.github.PrzeBarCore.springlibraryworkshop.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

@ControllerAdvice
class ExceptionsController {

    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(IllegalStateException.class)
    ResponseEntity<?> handleIllegalStateException(IllegalStateException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }
    @ExceptionHandler(ConstraintViolationException.class)
    ResponseEntity<?> handleJavaxException(ConstraintViolationException e){
        return ResponseEntity.badRequest().body(e.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage));
    }

    //for developing
    @ExceptionHandler(BindException.class)
    ResponseEntity<?> handleException(BindException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
