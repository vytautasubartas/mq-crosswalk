package com.uvytautas.mqcrosswalk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableAsync
@EnableJms
public class MqCrosswalkApplication {

	public static void main(String[] args) {
		SpringApplication.run(MqCrosswalkApplication.class, args);
	}

}
