package com.wojciechbarwinski.demo.legendary_warehouse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class LegendaryWarehouseApplication {

	public static void main(String[] args) {
		SpringApplication.run(LegendaryWarehouseApplication.class, args);
	}

}
