package com.stayFinder.proyectoFinal.exceptionHandling.advices;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
class GlobalExceptionAdvice {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseMessage<String>> exceptionHandler (Exception e){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
            new ErrorResponseMessage<String>(false, e.getMessage())
        );
    }

    
}
