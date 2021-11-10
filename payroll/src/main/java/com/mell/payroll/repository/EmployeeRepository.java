package com.mell.payroll.repository;

import com.mell.payroll.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//NOTE: Domain type -> Employee, Id type -> Long
public interface EmployeeRepository extends JpaRepository<Employee,Long>
{
}
