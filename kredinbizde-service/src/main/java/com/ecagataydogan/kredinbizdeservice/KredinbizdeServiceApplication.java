package com.ecagataydogan.kredinbizdeservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class KredinbizdeServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(KredinbizdeServiceApplication.class, args);
	}

}
