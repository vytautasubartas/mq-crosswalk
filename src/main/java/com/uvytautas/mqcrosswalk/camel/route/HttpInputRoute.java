package com.uvytautas.mqcrosswalk.camel.route;

import com.uvytautas.mqcrosswalk.camel.processor.logging.TraceLogProcessor;
import com.uvytautas.mqcrosswalk.camel.util.CommonConstants;
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
        from(CommonConstants.Route.HTTP_INPUT.getUri())
                .routeId(CommonConstants.Route.HTTP_INPUT.getId())
                .process(traceLogProcessor)
                .inOnly(ibmUriBased.getUri());
    }
}
