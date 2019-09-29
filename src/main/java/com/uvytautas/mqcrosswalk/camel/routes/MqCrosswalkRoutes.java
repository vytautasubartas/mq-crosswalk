package com.uvytautas.mqcrosswalk.camel.routes;

import com.uvytautas.mqcrosswalk.camel.processors.DocumentProcessor;
import com.uvytautas.mqcrosswalk.camel.processors.TraceLogProcessor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class MqCrosswalkRoutes extends RouteBuilder {


    private final TraceLogProcessor traceLogProcessor;
    private final DocumentProcessor documentProcessor;

    public MqCrosswalkRoutes(TraceLogProcessor traceLogProcessor, DocumentProcessor documentProcessor) {
        this.traceLogProcessor = traceLogProcessor;
        this.documentProcessor = documentProcessor;
    }

    @Override
    public void configure() {

        from("jetty:http://0.0.0.0:9999/input")
                .process(traceLogProcessor)
                .to("ibmmq:queue:DEV.QUEUE.1");

        from("ibmmq:queue:DEV.QUEUE.1")
                .process(traceLogProcessor)
                .process(documentProcessor)
                .inOnly("activemq:queue:ibmmqconsumer");
    }
}
