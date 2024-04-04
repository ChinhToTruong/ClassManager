package com.example.classmanager.exception;

import com.example.classmanager.dto.response.ApiResponse;
import com.example.classmanager.exception.custom.CommonException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Log4j2
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalException(Exception ex) {
        log.error("handleGlobalException: ", ex);
        return ApiResponse.build()
                .withSuccess(false)
                .withHttpStatus(HttpStatus.BAD_REQUEST)
                .withMessage(ex.getMessage())
                .toEntity();
    }

    @ExceptionHandler(CommonException.class)
    public ResponseEntity<?> handleCommonException(CommonException ex){
        log.error("handleCommonException: ", ex);
        return ApiResponse.build()
                .withSuccess(false)
                .withHttpStatus(HttpStatus.BAD_REQUEST)
                .withMessage(ex.getMessage())
                .toEntity();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        Map<String, Object> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(
                (error) -> {
                    String title = error.getObjectName();
                    String message = error.getDefaultMessage();
                    errors.put(title, message);
                }
        );
        return ApiResponse.build()
                .withHttpStatus(HttpStatus.BAD_REQUEST)
                .withErrors(errors)
                .withSuccess(false)
                .toEntity();
    }
}
