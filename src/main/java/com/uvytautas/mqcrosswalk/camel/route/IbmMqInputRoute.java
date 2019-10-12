package com.uvytautas.mqcrosswalk.camel.route;

import com.uvytautas.mqcrosswalk.camel.processor.header.PayloadHeaderProcessor;
import com.uvytautas.mqcrosswalk.camel.processor.logging.TraceLogProcessor;
import com.uvytautas.mqcrosswalk.camel.util.CommonConstants;
import com.uvytautas.mqcrosswalk.camel.util.DocumentTypes;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class IbmMqInputRoute extends RouteBuilder implements UriBased {
    private final String uri;

    private final TraceLogProcessor traceLogProcessor;
    private final PayloadHeaderProcessor payloadHeaderProcessor;

    public IbmMqInputRoute(TraceLogProcessor traceLogProcessor, PayloadHeaderProcessor payloadHeaderProcessor) {
        this.traceLogProcessor = traceLogProcessor;
        this.uri = "ibmmq:queue:DEV.QUEUE.1";
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
                .choice().when(header(CommonConstants.DOCUMENT_TYPE_HEADER).isEqualTo(DocumentTypes.MASTER.toString()))
                .to("direct:master")
                .endChoice()
                .when(header(CommonConstants.DOCUMENT_TYPE_HEADER).isEqualTo(DocumentTypes.UPDATE.toString()))
                .to("direct:update")
                .endChoice()
                .otherwise().log("UNKNOWN DOC TYPE")
                .end()
                .to("activemq:queue:ibmmqconsumer");
    }
}
