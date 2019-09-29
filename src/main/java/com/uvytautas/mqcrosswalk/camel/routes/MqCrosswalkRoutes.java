package com.uvytautas.mqcrosswalk.camel.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class MqCrosswalkRoutes extends RouteBuilder {

    @Override
    public void configure() {

        from("jetty:http://0.0.0.0:9999/input").to("ibmmq:queue:DEV.QUEUE.1");

        from("ibmmq:queue:DEV.QUEUE.1")
                .log("Passing to activemq")
                .inOnly("activemq:queue:ibmmqconsumer");
    }
}
