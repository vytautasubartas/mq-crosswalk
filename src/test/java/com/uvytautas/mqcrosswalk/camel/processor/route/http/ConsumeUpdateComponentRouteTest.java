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
        classes = ConsumeUpdateComponentRouteTest.class
)
@TestConfiguration
public class ConsumeUpdateComponentRouteTest extends AbstractComponentRouteTest {

    private final String PAYLOAD = "<Document type=\"MASTER\" code=\"4443\">\n" +
            "<Points>11</Points>\n" +
            "<Assists>4</Assists>\n" +
            "</Document>";

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
        mockEndpoint.message(0).body(String.class).isEqualTo(createResponsePayload());//where to mock updates
    }

    private String createResponsePayload() {
        return PAYLOAD;
    }

    @Override
    protected String getEndpointToTest() {
        return CommonConstants.Route.DOCUMENT_UPDATE.getId();
    }

    @Override
    protected void configureAdvice(final AdviceWithRouteBuilder adviceWithRouteBuilder) {
        adviceWithRouteBuilder.weaveByToUri("*").replace().to(MOCK_ENDPOINT);
    }
}
