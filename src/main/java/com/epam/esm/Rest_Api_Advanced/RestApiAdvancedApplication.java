package com.epam.esm.Rest_Api_Advanced;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaAuditing
@EnableJpaRepositories
public class RestApiAdvancedApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestApiAdvancedApplication.class, args);
	}

}
