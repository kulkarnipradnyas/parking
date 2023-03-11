package com.parkinglot.parkinglotfees;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
		"com.parkinglot.parkinglotfees.*"
		})
public class ParkinglotFeesApplication {

	public static void main(String[] args) {
		SpringApplication.run(ParkinglotFeesApplication.class, args);
	}

}
