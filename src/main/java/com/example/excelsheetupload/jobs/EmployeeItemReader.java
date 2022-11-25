package com.example.excelsheetupload.jobs;

/*
 * Copyright (c) 2022 Md Wasif Ali.
 */

import com.example.excelsheetupload.entities.Employee;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.extensions.excel.RowMapper;
import org.springframework.batch.extensions.excel.mapping.BeanWrapperRowMapper;
import org.springframework.batch.extensions.excel.poi.PoiItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

/**
 * In this  Class contains methods to read data from Excel Sheet
 * records in our database
 *
 * @author Md Wasif Ali
 * @version 1.0
 * @since 25/11/22
 */
@Component
public class EmployeeItemReader {

    @Bean
    @StepScope
    public PoiItemReader excelReader(@Value("#{jobParameters['FILE_PATH']}") String filePath) {
        PoiItemReader reader = new PoiItemReader();
        reader.setResource(new FileSystemResource(filePath));
        reader.setRowMapper(rowMapper());
        return reader;
    }

    @Bean
    public RowMapper rowMapper() {
        BeanWrapperRowMapper<Employee> rowMapper = new BeanWrapperRowMapper<>();
        rowMapper.setTargetType(Employee.class);
        return rowMapper;
    }
}
