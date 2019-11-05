package com.uvytautas.mqcrosswalk.camel.route;

import com.uvytautas.mqcrosswalk.camel.processor.document.UpdateDocumentProcessor;
import com.uvytautas.mqcrosswalk.camel.util.CommonConstants;
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
        from(CommonConstants.Route.DOCUMENT_UPDATE.getUri()).log("Update received")
                .routeId(CommonConstants.Route.DOCUMENT_UPDATE.getUri())
                .to("xslt:xslt/initial_transform.xsl")
                .process(updateDocumentProcessor);
    }
}
