package com.mell.payroll.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class OrderNotFoundAdvice
{
    //NOTE: @ResponseBody -> this advice is rendered into the response body
    @ResponseBody
    //NOTE: @ExceptionHandler -> configures the advice to only respond if an EmployeeNotFoundException is thrown.
    @ExceptionHandler(OrderNotFoundException.class)
    //NOTE: @ResponseStatus -> says to issue an HttpStatus.NOT_FOUND, i.e. an HTTP 404.
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String orderNotFoundHandler(OrderNotFoundException ex)
    {
        return ex.getMessage();
    }
}
