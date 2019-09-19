package com.uvytautas.mqcrosswalk.routes;

import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MqCrosswalkRoutes extends RouteBuilder {
    private static final Logger LOGGER = LoggerFactory.getLogger(MqCrosswalkRoutes.class);

    @Override
    public void configure() {
        from("ibmmq:queue:DEV.QUEUE.1").process(exchange -> LOGGER.info("Received message from IBM MQ: {}", exchange.getMessage().getBody()));
    }
}
