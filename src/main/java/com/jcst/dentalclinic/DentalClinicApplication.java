package com.jcst.dentalclinic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.jcst.dentalclinic"})
public class DentalClinicApplication {

	public static void main(String[] args) {
		SpringApplication.run(DentalClinicApplication.class, args);
	}
}
