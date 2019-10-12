package com.uvytautas.mqcrosswalk.camel.route;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class HealthCheckRoute extends RouteBuilder {
    @Override
    public void configure() {
        from("netty4-http:http://0.0.0.0:9999/healthcheck").log(LoggingLevel.INFO, "Health Check ping");
    }
}
