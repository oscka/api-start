package com.osckorea.openmsa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan("com.osckorea.openmsa")
@SpringBootApplication
public class OpenMsaApplication {

	public static void main(String[] args) {
		SpringApplication.run(OpenMsaApplication.class, args);
	}

}
