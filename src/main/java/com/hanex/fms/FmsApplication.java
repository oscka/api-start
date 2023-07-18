package com.hanex.fms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan("com.hanex.fms")
@SpringBootApplication
public class FmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(FmsApplication.class, args);
	}

}
