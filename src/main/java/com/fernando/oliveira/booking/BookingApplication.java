package com.fernando.oliveira.booking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fernando.oliveira.booking.api.converter.Java8DateTimeConfiguration;

@SpringBootApplication
public class BookingApplication{


	public static void main(String[] args) {
		SpringApplication.run(BookingApplication.class, args);
	}
	
	

}
