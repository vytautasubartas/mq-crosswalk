package com.uvytautas.mqcrosswalk.camel.route;

import com.uvytautas.mqcrosswalk.camel.exception.DocumentTypeNotFoundException;
import com.uvytautas.mqcrosswalk.camel.util.CommonConstants;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class DocumentGatewayRoute extends RouteBuilder {
    @Override
    public void configure() {
        from(CommonConstants.Route.DOCUMENT_GATEWAY.getUri()).choice().when(header(CommonConstants.DOCUMENT_TYPE_HEADER).isEqualTo(CommonConstants.DocumentTypes.MASTER.toString()))
                .to(CommonConstants.Route.DOCUMENT_MASTER.getUri())
                .endChoice()
                .when(header(CommonConstants.DOCUMENT_TYPE_HEADER).isEqualTo(CommonConstants.DocumentTypes.UPDATE.toString()))
                .to(CommonConstants.Route.DOCUMENT_UPDATE.getUri())
                .endChoice()
                .otherwise().throwException(new DocumentTypeNotFoundException("error.doc-type.not-found"))
                .end();
    }
}
