package com.rmit.demo.config;

import org.hibernate.exception.DataException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.ClassUtils;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NullPointerException.class)
    private ResponseEntity<Object> handleNullException(NullPointerException ex) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.clear();
        map.put("timestamp", new Date());
        map.put("status", HttpStatus.NOT_FOUND.value());
        map.put("isSuccess", false);
        map.put("message", "No resources found.");
        map.put("data", null);
        return new ResponseEntity<Object>(map, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    private ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.clear();
        map.put("timestamp", new Date());
        map.put("status", HttpStatus.BAD_REQUEST.value());
        map.put("isSuccess", false);
        map.put("message", "IllegalArgumentException. " + ex);
        map.put("data", null);
        return new ResponseEntity<Object>(map, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(Throwable.class)
    private ResponseEntity<Object> handleAnyException(Throwable ex, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.clear();
        map.put("timestamp", new Date());
        map.put("status", HttpStatus.BAD_REQUEST.value());
        map.put("isSuccess", false);
        map.put("message", "Bad Request " + ex);
        map.put("data", null);
        ex.printStackTrace();
        return new ResponseEntity<Object>(map, HttpStatus.BAD_REQUEST);
    }


}
