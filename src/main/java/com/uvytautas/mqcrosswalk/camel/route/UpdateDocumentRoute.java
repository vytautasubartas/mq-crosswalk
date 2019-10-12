package com.uvytautas.mqcrosswalk.camel.route;

import com.uvytautas.mqcrosswalk.camel.processor.document.UpdateDocumentProcessor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class UpdateDocumentRoute extends RouteBuilder {
    private final UpdateDocumentProcessor updateDocumentProcessor;

    public UpdateDocumentRoute(UpdateDocumentProcessor updateDocumentProcessor) {
        this.updateDocumentProcessor = updateDocumentProcessor;
    }

    @Override
    public void configure() {
        from("direct:update").log("Update received")
                .process(updateDocumentProcessor);
    }
}
