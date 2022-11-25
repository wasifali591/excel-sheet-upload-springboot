package com.example.excelsheetupload;

import com.example.excelsheetupload.entities.AppUser;
import com.example.excelsheetupload.entities.Role;
import com.example.excelsheetupload.services.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class ExcelsheetUploadApplication {



	public static void main(String[] args) {
		SpringApplication.run(ExcelsheetUploadApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

	@Bean
	CommandLineRunner run(AppUserService userService){
		return args -> {
			userService.saveRole(new Role(null, "USER"));
			userService.saveRole(new Role(null, "ADMIN"));

			userService.saveUser(new AppUser(null, "wasif", "wasif","12345", new ArrayList<>()));
			userService.saveUser(new AppUser(null, "parinitha", "parinitha","12345", new ArrayList<>()));

			userService.addRoleToUser("wasif","ADMIN");
			userService.addRoleToUser("wasif","USER");

			userService.addRoleToUser("parinitha","USER");
		};
	}


}
