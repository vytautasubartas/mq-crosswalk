package com.uvytautas.mqcrosswalk.camel.route;

import com.uvytautas.mqcrosswalk.camel.processor.document.MasterDocumentProcessor;
import com.uvytautas.mqcrosswalk.camel.util.CommonConstants;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class MasterDocumentRoute extends RouteBuilder {

    private final MasterDocumentProcessor masterDocumentProcessor;

    public MasterDocumentRoute(MasterDocumentProcessor masterDocumentProcessor) {
        this.masterDocumentProcessor = masterDocumentProcessor;
    }

    @Override
    public void configure() {
        from(CommonConstants.Route.DOCUMENT_MASTER.getUri()).log("Master received")
                .routeId(CommonConstants.Route.DOCUMENT_MASTER.getId())
                .to("xslt:xslt/initial_transform.xsl")
                .process(masterDocumentProcessor);

    }
}
