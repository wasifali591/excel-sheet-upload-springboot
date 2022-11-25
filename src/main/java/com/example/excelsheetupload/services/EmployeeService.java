package com.example.excelsheetupload.services;

/*
 * Copyright (c) 2022 Md Wasif Ali.
 */

import com.example.excelsheetupload.entities.Employee;
import com.example.excelsheetupload.entities.File;

import java.util.List;

/**
 * This is a service interface which provides required crud operation for {@link Employee}.
 *
 * @author Md Wasif Ali
 * @version 1.0
 * @since 25/11/22
 */
public interface EmployeeService {
    /**
     * this method used to create a record in {@link Employee}
     * @param employee
     * @return Employee
     */
    public Employee saveEmployee(Employee employee);

    /**
     * This method is used to get all the {@link Employee}
     *
     * @return List of Employee
     */
    public List<Employee> getEmployee();

    /**
     * This method is used to get All the {@link Employee} related to a specific {@link File}
     * @param Id
     * @return List of Employee
     */
    public List<Employee> getEmployeeByFileId(Long Id);
}
