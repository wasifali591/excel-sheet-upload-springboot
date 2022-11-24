package com.example.excelsheetupload.services.impl;

import com.example.excelsheetupload.dtos.requests.EmployeeRequestDto;
import com.example.excelsheetupload.entities.Employee;
import com.example.excelsheetupload.entities.File;
import com.example.excelsheetupload.repositories.EmployeeRepository;
import com.example.excelsheetupload.repositories.FileRepository;
import com.example.excelsheetupload.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private FileRepository fileRepository;

    @Override
    public Employee saveEmployee(Employee employee, Long id) {
        File file = fileRepository.findById(id).get();
        employee.setFile(file);
        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> getEmployee(){
        return employeeRepository.findAll();
    }

}
