package com.sid.iauro.jwt.ProductManagementJWT;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProductManagementJwtApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductManagementJwtApplication.class, args);
		System.out.println("Application is running on 8084 for JWT");
	}

}
