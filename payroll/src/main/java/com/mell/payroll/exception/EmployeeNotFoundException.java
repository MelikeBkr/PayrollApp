package com.mell.payroll.exception;

public class EmployeeNotFoundException extends RuntimeException
{
    public EmployeeNotFoundException(Long id)
    {
        //NOTE: will invoke public RuntimeException(String message)
        super("Could not find employee "+id);
    }
}
