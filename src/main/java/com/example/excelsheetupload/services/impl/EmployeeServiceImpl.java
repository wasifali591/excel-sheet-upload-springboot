package com.example.excelsheetupload.services.impl;

import com.example.excelsheetupload.entities.Employee;
import com.example.excelsheetupload.repositories.EmployeeRepository;
import com.example.excelsheetupload.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }
}
