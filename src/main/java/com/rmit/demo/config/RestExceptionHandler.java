package com.rmit.demo.config;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;
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
}
