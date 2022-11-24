package com.example.excelsheetupload.services;

import com.example.excelsheetupload.entities.Employee;
import org.springframework.stereotype.Service;

import java.util.List;


public interface EmployeeService {
    /**
     * save Employee
     * @return
     */
    public Employee saveEmployee(Employee employee, Long id);
    public List<Employee> getEmployee();
}
