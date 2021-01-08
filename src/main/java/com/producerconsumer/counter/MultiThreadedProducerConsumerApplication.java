package com.producerconsumer.counter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MultiThreadedProducerConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MultiThreadedProducerConsumerApplication.class, args);
	}
}
