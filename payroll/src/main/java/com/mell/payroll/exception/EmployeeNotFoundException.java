package com.mell.payroll.exception;

public class EmployeeNotFoundException extends RuntimeException
{
    public EmployeeNotFoundException(Long id)
    {
        // will invoke public RuntimeException(String message)
        super("Could not find employee "+id);
    }
}
