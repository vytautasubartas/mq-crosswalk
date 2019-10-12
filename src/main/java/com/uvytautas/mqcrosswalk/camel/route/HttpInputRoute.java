package com.uvytautas.mqcrosswalk.camel.route;

import com.uvytautas.mqcrosswalk.camel.processor.logging.TraceLogProcessor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class HttpInputRoute extends RouteBuilder {
    private final UriBased ibmUriBased;

    private final TraceLogProcessor traceLogProcessor;

    public HttpInputRoute(UriBased ibmUriBased, TraceLogProcessor traceLogProcessor) {
        this.ibmUriBased = ibmUriBased;
        this.traceLogProcessor = traceLogProcessor;
    }

    @Override
    public void configure() {
        from("netty4-http:http://0.0.0.0:9999/input")
                .process(traceLogProcessor)
                .inOnly(ibmUriBased.getUri());
    }
}
