package com.example.excelsheetupload;

import com.example.excelsheetupload.entities.Employee;
import com.example.excelsheetupload.entities.File;
import com.example.excelsheetupload.services.EmployeeService;
import com.example.excelsheetupload.services.impl.EmployeeServiceImpl;
import com.example.excelsheetupload.services.impl.FileServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;

@SpringBootApplication
public class ExcelsheetUploadApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExcelsheetUploadApplication.class, args);
	}


}
