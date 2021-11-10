package com.mell.payroll.repository;

import com.mell.payroll.model.Employee;
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
   CommandLineRunner initDatabase(EmployeeRepository repository)
   {
       return args ->
       {
           //NOTE: will create two entities as follow and store them
           log.info("Preloading "+ repository.save(new Employee("Elizabeth", "Brooklyn","Account Manager")));
           log.info("Preloading "+ repository.save(new Employee("David", "Harvey","Salesperson")));
       };
   }
}
