package com.example.excelsheetupload.jobs;

import com.example.excelsheetupload.entities.Employee;
import com.example.excelsheetupload.entities.File;
import com.example.excelsheetupload.repositories.FileRepository;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@StepScope
public class EmployeeItemProcessor implements ItemProcessor<Employee, Employee> {

    @Autowired
    private FileRepository fileRepository;

    @Value("#{jobParameters['FILE_ID']}")
    private Long fileId;
    @Override
    public Employee process(Employee employee) throws Exception {
        System.out.println("=================="+ fileId + "==================");
        File file = fileRepository.findById(fileId).get();
        //Todo: it will call for eatch row - optimize
        employee.setFile(file);
        return employee;
    }
}
