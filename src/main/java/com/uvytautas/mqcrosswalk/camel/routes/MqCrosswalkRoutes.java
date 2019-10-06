package com.uvytautas.mqcrosswalk.camel.routes;

import com.uvytautas.mqcrosswalk.camel.processors.document.MasterDocumentProcessor;
import com.uvytautas.mqcrosswalk.camel.processors.document.UpdateDocumentProcessor;
import com.uvytautas.mqcrosswalk.camel.processors.logging.TraceLogProcessor;
import com.uvytautas.mqcrosswalk.camel.util.Constants;
import com.uvytautas.mqcrosswalk.camel.util.DocumentTypes;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class MqCrosswalkRoutes extends RouteBuilder {


    private final TraceLogProcessor traceLogProcessor;
    private final MasterDocumentProcessor masterDocumentProcessor;
    private final UpdateDocumentProcessor updateDocumentProcessor;

    public MqCrosswalkRoutes(TraceLogProcessor traceLogProcessor, MasterDocumentProcessor masterDocumentProcessor, UpdateDocumentProcessor updateDocumentProcessor) {
        this.traceLogProcessor = traceLogProcessor;
        this.masterDocumentProcessor = masterDocumentProcessor;
        this.updateDocumentProcessor = updateDocumentProcessor;
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
                .setHeader(Constants.DOCUMENT_TYPE_HEADER).xpath("/Document/@type", String.class)
                .choice().when(header(Constants.DOCUMENT_TYPE_HEADER).isEqualTo(DocumentTypes.MASTER.toString()))
                .log("Master received")
                .process(masterDocumentProcessor)
                .endChoice()
                .when(header(Constants.DOCUMENT_TYPE_HEADER).isEqualTo(DocumentTypes.UPDATE.toString()))
                .log("Update received")
                .process(updateDocumentProcessor)
                .endChoice()
                .otherwise().log("UNKNOWN DOC TYPE")
                .end()
                .to("activemq:queue:ibmmqconsumer");
    }
}
