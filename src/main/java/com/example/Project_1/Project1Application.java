package com.example.Project_1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;


@SpringBootApplication
@EnableMethodSecurity
public class Project1Application {


	public static void main(String[] args) {
		SpringApplication.run(Project1Application.class, args);

/// Environment variable
		String dbUrl = System.getenv("DB_URL");
		if (dbUrl != null){
			System.out.println("Database URL: " + dbUrl);
		} else {
			System.out.println("DB_URL environment variable is not set.");
		}
	}
}