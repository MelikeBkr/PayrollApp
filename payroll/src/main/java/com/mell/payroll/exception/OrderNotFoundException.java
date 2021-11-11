package com.mell.payroll.exception;

public class OrderNotFoundException extends RuntimeException
{
    public OrderNotFoundException(Long id)
    {
        //NOTE: will invoke public RuntimeException(String message)
        super("Could not find order "+id);
    }
}
