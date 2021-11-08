package com.mell.payroll.controller;

import com.mell.payroll.model.Employee;
import com.mell.payroll.model.EmployeeNotFoundException;
import com.mell.payroll.repository.EmployeeRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//the data returned by each method will be written straight into the response body
// instead of rendering a template.
@RestController
public class EmployeeController
{
    private final EmployeeRepository repository;

    public EmployeeController(EmployeeRepository repository) {
        this.repository = repository;
    }
    @GetMapping("/employees")
    List<Employee> getAllEmployees()
    {
        return repository.findAll();
    }
    @RequestMapping("/employees")
    Employee addNewEmployee(@RequestBody Employee newEmployee)
    {
        return repository.save(newEmployee);
    }
    @GetMapping("/employees/{id}")
    Employee getEmployeesWithId(@PathVariable Long id)
    {
        return repository.findById(id)
                .orElseThrow(()-> new EmployeeNotFoundException(id));
    }
    @PutMapping("/employees/{id}")
    Employee replaceEmployee(@RequestBody Employee newEmployee, @PathVariable Long id)
    {
        return repository.findById(id)
                .map(employee ->
                {
                    employee.setName(newEmployee.getName());
                    employee.setRole(newEmployee.getRole());
                    return repository.save(employee);
                }).orElseGet(()->{
                    newEmployee.setId(id);
                    return repository.save(newEmployee);
                });
    }
    @DeleteMapping("/employees/{id}")
    void deleteEmployee(@PathVariable Long id)
    {
        repository.deleteById(id);
    }

}
