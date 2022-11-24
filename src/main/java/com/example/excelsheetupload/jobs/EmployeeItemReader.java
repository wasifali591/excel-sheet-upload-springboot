package com.example.excelsheetupload.jobs;

import com.example.excelsheetupload.entities.Employee;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.extensions.excel.RowMapper;
import org.springframework.batch.extensions.excel.mapping.BeanWrapperRowMapper;
import org.springframework.batch.extensions.excel.poi.PoiItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

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
