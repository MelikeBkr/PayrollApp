package com.mell.payroll.assembler;

import com.mell.payroll.controller.EmployeeController;
import com.mell.payroll.model.Employee;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

//NOTE: RepresentationModelAssembler interface allows you to define a function that converts Employee objects
//to EntityModel<Employee>
@Component
public class EmployeeModelAssembler implements RepresentationModelAssembler<Employee, EntityModel<Employee>>
{
    @Override
    //NOTE: toModel() converts a non-model object (Employee)
    // into a model-based object (EntityModel<Employee>)
    public EntityModel<Employee> toModel(Employee employee)
    {
        return EntityModel.of(employee,
                linkTo(methodOn(EmployeeController.class).getEmployeeById(employee.getId())).withSelfRel(),
                linkTo(methodOn(EmployeeController.class).getAllEmployees()).withRel("employees"));
    }
}
