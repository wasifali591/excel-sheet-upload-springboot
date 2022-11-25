package com.example.excelsheetupload.services.impl;

/*
 * Copyright (c) 2022 Md Wasif Ali.
 */

import com.example.excelsheetupload.entities.Employee;
import com.example.excelsheetupload.repositories.EmployeeRepository;
import com.example.excelsheetupload.repositories.FileRepository;
import com.example.excelsheetupload.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This class implement an interface {@link EmployeeService}
 * It contain different business logic for {@link Employee}
 *
 * @author Md Wasif Ali
 * @version 1.0
 * @since 25/11/22
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private FileRepository fileRepository;

    /**
     *  This method used to Save {@link Employee}
     *
     * @param employee
     * @return Employee
     */
    @Override
    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    /**
     * This method used to get all the {@link Employee}
     *
     * @return List of Employee
     */
    @Override
    public List<Employee> getEmployee(){
        return employeeRepository.findAll();
    }

    /**
     *
     * @param fileId
     * @return
     */
    @Override
    public List<Employee> getEmployeeByFileId(Long fileId){
        return employeeRepository.findByFileId(fileId);
    }

}
