package com.skillshare.login_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class LoginServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoginServiceApplication.class, args);
	}

}