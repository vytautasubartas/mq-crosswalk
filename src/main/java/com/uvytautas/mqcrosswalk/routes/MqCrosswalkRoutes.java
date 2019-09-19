package com.uvytautas.mqcrosswalk.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class MqCrosswalkRoutes extends RouteBuilder {

    @Override
    public void configure() {
        from("ibmmq:queue:DEV.QUEUE.1")
                .log("Passing to activemq")
                .to("activemq:queue:ibmmqconsumer");
    }
}
