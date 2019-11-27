package com.uvytautas.mqcrosswalk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableJms
public class MqCrosswalkApplication {

    public static void main(String[] args) {
        SpringApplication.run(MqCrosswalkApplication.class, args);
    }

}
