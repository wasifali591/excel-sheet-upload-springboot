package com.example.excelsheetupload.repositories;

/*
 * Copyright (c) 2022 Md Wasif Ali.
 */

import com.example.excelsheetupload.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * This is a repository interface which provides required crud operation for {@link Employee}.
 ** its extends {@link JpaRepository}
 *
 * @author Md Wasif Ali
 * @version 1.0
 * @since 25/11/22
 */
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    /**
     * Get {@link Employee} by using File Id
     *
     * @param Id File Id
     * @return List of Employee
     */
    List<Employee> findByFileId(Long Id);
}
