package com.ispan.dogland;

import com.ispan.dogland.dao.OrderDetailRepository;
import com.ispan.dogland.dao.OrdersRepository;
import com.ispan.dogland.entity.OrderDetail;
import com.ispan.dogland.entity.Orders;
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
	public CommandLineRunner commandLineRunner(OrderDetailRepository orderDetailRepository) {
		return runner -> {
			System.out.println("Hello World");

			for(OrderDetail orderDetail : orderDetailRepository.findAll()) {
				System.out.println(orderDetail);
			}


		};
	}


}


