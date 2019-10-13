package com.uvytautas.mqcrosswalk.camel.route;

import com.uvytautas.mqcrosswalk.camel.exception.DocumentTypeNotFoundException;
import com.uvytautas.mqcrosswalk.camel.util.CommonConstants;
import com.uvytautas.mqcrosswalk.camel.util.DocumentTypes;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class DocumentGatewayRoute extends RouteBuilder {
    @Override
    public void configure() {
        from("direct:documentGateway").choice().when(header(CommonConstants.DOCUMENT_TYPE_HEADER).isEqualTo(DocumentTypes.MASTER.toString()))
                .to("direct:master")
                .endChoice()
                .when(header(CommonConstants.DOCUMENT_TYPE_HEADER).isEqualTo(DocumentTypes.UPDATE.toString()))
                .to("direct:update")
                .endChoice()
                .otherwise().throwException(new DocumentTypeNotFoundException("Document type not found"))
                .end();
    }
}
