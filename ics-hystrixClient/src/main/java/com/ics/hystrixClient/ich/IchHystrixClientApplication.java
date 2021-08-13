package com.ics.hystrixClient.ich;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;

@SpringBootApplication
@EnableCircuitBreaker
public class IchHystrixClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(IchHystrixClientApplication.class, args);
	}

}
