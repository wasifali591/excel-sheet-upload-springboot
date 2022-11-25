package com.example.excelsheetupload.jobs;

/*
 * Copyright (c) 2022 Md Wasif Ali.
 */

import com.example.excelsheetupload.entities.Employee;
import com.example.excelsheetupload.entities.File;
import com.example.excelsheetupload.repositories.FileRepository;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * In this Processor Class we are modifying the data which will be compatible to create
 * records in our database
 * its implements {@link ItemProcessor}.
 *
 * @author Md Wasif Ali
 * @version 1.0
 * @since 25/11/22
 */
@Configuration
@StepScope
public class EmployeeItemProcessor implements ItemProcessor<Employee, Employee> {

    @Autowired
    private FileRepository fileRepository;

    @Value("#{jobParameters['FILE_ID']}")
    private Long fileId;
    @Override
    public Employee process(Employee employee) throws Exception {
        File file = fileRepository.findById(fileId).get();
        //Todo: it will call for eatch row - optimize
        employee.setFile(file);
        return employee;
    }
}
