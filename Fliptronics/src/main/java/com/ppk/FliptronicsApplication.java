package com.ppk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
@EntityScan(basePackages = {"com.ppk"})
@SpringBootApplication
public class FliptronicsApplication {

	public static void main(String[] args) {
		System.out.println("spring started");
		SpringApplication.run(FliptronicsApplication.class, args);
		
	}

}
