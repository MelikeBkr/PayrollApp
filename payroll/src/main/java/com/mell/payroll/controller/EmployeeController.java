package com.mell.payroll.controller;

import com.mell.payroll.model.Employee;
import com.mell.payroll.exception.EmployeeNotFoundException;
import com.mell.payroll.repository.EmployeeRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

//the data returned by each method will be written straight into the response body
// instead of rendering a template.
@RestController
public class EmployeeController
{
    private final EmployeeRepository repository;

    public EmployeeController(EmployeeRepository repository) {
        this.repository = repository;
    }
    @GetMapping("/employees/{id}")
    EntityModel<Employee> getEmployeeById(@PathVariable Long id) {

        Employee employee = repository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
        return EntityModel.of(employee,
                linkTo(methodOn(EmployeeController.class).getEmployeeById(id)).withSelfRel(),
                linkTo(methodOn(EmployeeController.class).getAllEmployees()).withRel("employees"));
    }
    @GetMapping("/employees")
    CollectionModel<EntityModel<Employee>> getAllEmployees() {

        List<EntityModel<Employee>> employees = repository.findAll().stream()
                .map(employee -> EntityModel.of(employee,
                        linkTo(methodOn(EmployeeController.class).getEmployeeById(employee.getId())).withSelfRel(),
                        linkTo(methodOn(EmployeeController.class).getAllEmployees()).withRel("employees")))
                .collect(Collectors.toList());

        return CollectionModel.of(employees, linkTo(methodOn(EmployeeController.class).getAllEmployees()).withSelfRel());
    }
    @RequestMapping("/employees")
    Employee addNewEmployee(@RequestBody Employee newEmployee)
    {
        return repository.save(newEmployee);
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
