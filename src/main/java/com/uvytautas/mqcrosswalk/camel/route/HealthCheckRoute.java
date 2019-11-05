package com.uvytautas.mqcrosswalk.camel.route;

import com.uvytautas.mqcrosswalk.camel.util.CommonConstants;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class HealthCheckRoute extends RouteBuilder {
    @Override
    public void configure() {
        from(CommonConstants.Route.HEALTH_CHECK.getUri()).routeId(CommonConstants.Route.HEALTH_CHECK.getId()).log(LoggingLevel.INFO, "Health Check ping");
    }
}
