package com.uvytautas.mqcrosswalk.camel.processor.route.http;


import com.uvytautas.mqcrosswalk.camel.processor.route.AbstractComponentRouteTest;
import com.uvytautas.mqcrosswalk.camel.util.CommonConstants;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;

import java.io.ByteArrayInputStream;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.NONE,
        classes = ConsumeIbmMqComponentRouteTest.class
)
@TestConfiguration
public class ConsumeIbmMqComponentRouteTest extends AbstractComponentRouteTest {
    private static final String PAYLOAD = "<Document type=\"MASTER\" code=\"4443\">\n" +
            "<Points>11</Points>\n" +
            "<Assists>4</Assists>\n" +
            "</Document>";

    @Override
    protected void sendPayload(final ProducerTemplate producerTemplate) {
        producerTemplate.sendBody(new ByteArrayInputStream(PAYLOAD.getBytes()));
    }

    @Override
    protected void assertMock(final MockEndpoint mockEndpoint) {
        mockEndpoint.expectedMessageCount(2); // why two messages
        mockEndpoint.message(0).header(CommonConstants.DOCUMENT_CODE_HEADER).isEqualTo("4443");
        mockEndpoint.message(0).header(CommonConstants.DOCUMENT_TYPE_HEADER).isEqualTo("MASTER");
        mockEndpoint.message(0).body(String.class).isEqualTo(createResponsePayload());
    }

    private String createResponsePayload() {
        return PAYLOAD;
    }

    @Override
    protected String getEndpointToTest() {
        return CommonConstants.Route.IBMMQ_INPUT.getId();
    }

    @Override
    protected void configureAdvice(final AdviceWithRouteBuilder adviceWithRouteBuilder) {
        adviceWithRouteBuilder.weaveByToUri("*").replace().to(MOCK_ENDPOINT);
    }
}
