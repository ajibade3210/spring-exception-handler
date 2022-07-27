package com.bafoly.ex.error;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

//@RestControllerAdvice
public class ErrorAdvice {

    @ExceptionHandler(NothingFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public AppError handleNothingFoundException(NothingFoundException exception, HttpServletRequest request){
        AppError error = new AppError(404, exception.getMessage(), request.getServletPath());
        return error;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public  AppError handleMethodArgumentNotValidException (MethodArgumentNotValidException exception, HttpServletRequest request){
        AppError error = new AppError(400, exception.getMessage(), request.getServletPath());
        BindingResult bindingResult = exception.getBindingResult();
        Map<String, String> vallidationErrors = new HashMap<>();
        for(FieldError fieldError : bindingResult.getFieldErrors()){
            vallidationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        error.setValidationErrors(vallidationErrors);
        return error;
    }
}
