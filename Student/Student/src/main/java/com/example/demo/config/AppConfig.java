package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import jakarta.annotation.PostConstruct;

@Configuration
@Profile("dev")
public class AppConfig {

	@PostConstruct
	public void print() {
	System.out.println("This class is for profile configuration");

	}
}
