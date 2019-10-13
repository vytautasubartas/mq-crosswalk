package com.uvytautas.mqcrosswalk.camel.route;

import com.uvytautas.mqcrosswalk.camel.processor.header.PayloadHeaderProcessor;
import com.uvytautas.mqcrosswalk.camel.processor.logging.TraceLogProcessor;
import com.uvytautas.mqcrosswalk.camel.util.CommonConstants;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class IbmMqInputRoute extends RouteBuilder implements UriBased {
    private final String uri;

    private final TraceLogProcessor traceLogProcessor;
    private final PayloadHeaderProcessor payloadHeaderProcessor;

    public IbmMqInputRoute(TraceLogProcessor traceLogProcessor, PayloadHeaderProcessor payloadHeaderProcessor) {
        this.traceLogProcessor = traceLogProcessor;
        this.uri = CommonConstants.Route.IBMMQ_INPUT.getUri();
        this.payloadHeaderProcessor = payloadHeaderProcessor;
    }

    public String getUri() {
        return uri;
    }

    @Override
    public void configure() {

        from(uri)
                .process(traceLogProcessor)
                .process(payloadHeaderProcessor)
                .to(CommonConstants.Route.DOCUMENT_GATEWAY.getUri())
                .to(CommonConstants.Route.ACTIVEMQ_OUTPUT.getUri());
    }
}
