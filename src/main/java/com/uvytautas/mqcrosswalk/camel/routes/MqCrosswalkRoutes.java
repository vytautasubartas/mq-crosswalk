package com.uvytautas.mqcrosswalk.camel.routes;

import com.uvytautas.mqcrosswalk.camel.processors.DocumentProcessor;
import com.uvytautas.mqcrosswalk.camel.processors.TraceLogProcessor;
import com.uvytautas.mqcrosswalk.camel.util.Constants;
import org.apache.camel.LoggingLevel;
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

        from("netty4-http:http://0.0.0.0:9999/healthcheck").log(LoggingLevel.INFO, "Health Check ping");

        from("netty4-http:http://0.0.0.0:9999/input")
                .process(traceLogProcessor)
                .inOnly("ibmmq:queue:DEV.QUEUE.1");

        from("ibmmq:queue:DEV.QUEUE.1")
                .process(traceLogProcessor)
                .setHeader(Constants.DOCUMENT_CODE_HEADER).xpath("/Document/@code", String.class)
                .process(documentProcessor)
                .to("activemq:queue:ibmmqconsumer");
    }
}
