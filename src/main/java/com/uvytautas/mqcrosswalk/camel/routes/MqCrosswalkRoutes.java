package com.uvytautas.mqcrosswalk.camel.routes;

import com.uvytautas.mqcrosswalk.camel.processors.TraceLogProcessor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class MqCrosswalkRoutes extends RouteBuilder {


    private final TraceLogProcessor traceLogProcessor;

    public MqCrosswalkRoutes(TraceLogProcessor traceLogProcessor) {
        this.traceLogProcessor = traceLogProcessor;
    }

    @Override
    public void configure() {

        from("jetty:http://0.0.0.0:9999/input")
                .process(traceLogProcessor)
                .to("ibmmq:queue:DEV.QUEUE.1");

        from("ibmmq:queue:DEV.QUEUE.1")
                .process(traceLogProcessor)
                .inOnly("activemq:queue:ibmmqconsumer");
    }
}
