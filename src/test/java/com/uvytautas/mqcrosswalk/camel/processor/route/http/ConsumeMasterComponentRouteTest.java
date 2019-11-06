package com.uvytautas.mqcrosswalk.camel.processor.route.http;


import com.uvytautas.mqcrosswalk.camel.processor.route.AbstractComponentRouteTest;
import com.uvytautas.mqcrosswalk.camel.util.CommonConstants;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.NONE,
        classes = ConsumeMasterComponentRouteTest.class
)
@TestConfiguration
public class ConsumeMasterComponentRouteTest extends AbstractComponentRouteTest {
    private final String PAYLOAD = "<Document type=\"MASTER\" code=\"4443\">\n" +
            "<Points>11</Points>\n" +
            "<Assists>4</Assists>\n" +
            "</Document>";

    private final String RESPONSE = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Document><DocumentCode>4443</DocumentCode><Points>11</Points><Rebounds/><Assists>4</Assists><Steals/><Blocks/></Document>";

    @Override
    protected void sendPayload(final ProducerTemplate producerTemplate) {
        Map<String, Object> headers = new HashMap<>();
        headers.put(CommonConstants.DOCUMENT_CODE_HEADER, "4443");
        producerTemplate.sendBodyAndHeaders(PAYLOAD, headers);
    }

    @Override
    protected void assertMock(final MockEndpoint mockEndpoint) {
        mockEndpoint.expectedMessageCount(1);
        mockEndpoint.message(0).header(CommonConstants.DOCUMENT_CODE_HEADER).isEqualTo("4443");
        mockEndpoint.message(0).body(String.class).isEqualTo(createResponsePayload());// kaip xslt transforma verifyint
    }

    private String createResponsePayload() {
        return RESPONSE;
    }

    @Override
    protected String getEndpointToTest() {
        return CommonConstants.Route.DOCUMENT_MASTER.getId();
    }

    @Override
    protected void configureAdvice(final AdviceWithRouteBuilder adviceWithRouteBuilder) {
        adviceWithRouteBuilder.weaveAddLast().to(MOCK_ENDPOINT);
    }
}
