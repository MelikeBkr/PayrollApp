package com.mell.payroll.repository;

import com.mell.payroll.Status;
import com.mell.payroll.model.Employee;
import com.mell.payroll.model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase
{
   private static final Logger log= LoggerFactory.getLogger(LoadDatabase.class);
   //NOTE: This runner will request a copy of the EmployeeRepository
   @Bean
   CommandLineRunner initDatabase(EmployeeRepository employeeRepository, OrderRepository orderRepository)
   {
       return args ->
       {
           //NOTE: will create two entities as follow and store them
           employeeRepository.save(new Employee("Elizabeth", "Brooklyn","Account Manager"));
           employeeRepository.save(new Employee("David", "Harvey","Salesperson"));
           employeeRepository.findAll().forEach(employee -> log.info("Preloaded "+employee));

           orderRepository.save(new Order("MacBook Pro", Status.COMPLETED));
           orderRepository.save(new Order("iPhone", Status.IN_PROGRESS));
           orderRepository.findAll().forEach(order-> log.info("Preloaded "+order));
       };
   }
}
