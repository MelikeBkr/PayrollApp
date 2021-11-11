package com.mell.payroll.controller;

import com.mell.payroll.assembler.EmployeeModelAssembler;
import com.mell.payroll.model.Employee;
import com.mell.payroll.exception.EmployeeNotFoundException;
import com.mell.payroll.repository.EmployeeRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
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
    private final EmployeeModelAssembler assembler;


    public EmployeeController(EmployeeRepository repository, EmployeeModelAssembler assembler)
    {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/employees/{id}")
    public EntityModel<Employee> getEmployeeById(@PathVariable Long id) {

        Employee employee = repository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
        return assembler.toModel(employee);
    }
    @GetMapping("/employees")
    public CollectionModel<EntityModel<Employee>> getAllEmployees() {

        List<EntityModel<Employee>> employees = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(employees, linkTo(methodOn(EmployeeController.class).getAllEmployees()).withSelfRel());
    }
    @PostMapping("/employees")
    ResponseEntity<?> addNewEmployee(@RequestBody Employee newEmployee)
    {
        EntityModel<Employee> entityModel = assembler.toModel(repository.save(newEmployee));
        //NOTE: ResponseEntity is for creating a HTTP 201 Created status message
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @PutMapping("/employees/{id}")
    ResponseEntity<?> replaceEmployee(@RequestBody Employee newEmployee, @PathVariable Long id)
    {
        Employee updatedEmployee = repository.findById(id)
                .map(employee ->
                {
                    employee.setName(newEmployee.getName());
                    employee.setRole(newEmployee.getRole());
                    return repository.save(employee);
                }).orElseGet(()->{
                    newEmployee.setId(id);
                    return repository.save(newEmployee);
                });
        EntityModel<Employee> entityModel = assembler.toModel(updatedEmployee);
        //NOTE: entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri() will give
        //href link similar to this : http://localhost:8080/employees/{id}
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }
    @DeleteMapping("/employees/{id}")
    ResponseEntity<?> deleteEmployee(@PathVariable Long id)
    {
        repository.deleteById(id);
        //NOTE: this returns an HTTP 204 NO Content response
        return ResponseEntity.noContent().build();
    }

}
