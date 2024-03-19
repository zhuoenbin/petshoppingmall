package com.ispan.dogland;

import com.ispan.dogland.model.dao.OrderDetailRepository;
import com.ispan.dogland.model.dao.OrdersRepository;
import com.ispan.dogland.model.entity.OrderDetail;
import com.ispan.dogland.model.entity.Orders;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

@SpringBootApplication
public class DoglandApplication {

	public static void main(String[] args) {
		SpringApplication.run(DoglandApplication.class, args);
	}


	//測試方法
	@Bean
	public CommandLineRunner commandLineRunner(OrdersRepository orderRepository) {
		return runner -> {
			System.out.println("Hello World");
		};
	}


}


