package com.example.excelsheetupload.jobs;

/*
 * Copyright (c) 2022 Md Wasif Ali.
 */

import com.example.excelsheetupload.entities.Employee;
import com.example.excelsheetupload.repositories.EmployeeRepository;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * In this Configuration class we are writing the data which we are getting from Excel Sheet to database
 * its implements {@link ItemWriter}.
 *
 * @author Md Wasif Ali
 * @version 1.0
 * @since 25/11/22
 */
@Configuration
public class EmployeeItemWriter implements ItemWriter<Employee> {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public void write(List<? extends Employee> employees) throws Exception {

        employeeRepository.saveAll(employees);
    }
}
