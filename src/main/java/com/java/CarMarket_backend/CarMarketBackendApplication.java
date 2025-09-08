package com.java.CarMarket_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CarMarketBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarMarketBackendApplication.class, args);
	}

}
