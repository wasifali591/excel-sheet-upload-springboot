package com.example.excelsheetupload.repositories;

import com.example.excelsheetupload.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
