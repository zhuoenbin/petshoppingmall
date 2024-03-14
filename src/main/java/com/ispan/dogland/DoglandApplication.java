package com.ispan.dogland;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DoglandApplication {

	public static void main(String[] args) {
		SpringApplication.run(DoglandApplication.class, args);
	}


	//測試方法
	@Bean
	public CommandLineRunner commandLineRunner() {
		return runner -> {
			System.out.println("Hello World");
		};
	}


}


