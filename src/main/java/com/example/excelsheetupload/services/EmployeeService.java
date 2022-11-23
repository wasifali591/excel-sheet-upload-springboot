package com.example.excelsheetupload.services;

import com.example.excelsheetupload.entities.Employee;

public interface EmployeeService {
    /**
     * save Employee
     * @return
     */
    public Employee saveEmployee(Employee employee);
}
