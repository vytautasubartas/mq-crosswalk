package com.uvytautas.mqcrosswalk.camel.processor.route.http;

import com.uvytautas.mqcrosswalk.camel.processor.route.AbstractComponentRouteTest;
import com.uvytautas.mqcrosswalk.camel.util.CommonConstants;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.NONE,
        classes = ConsumeHttpComponentRouteTest.class
)
@TestConfiguration
public class ConsumeHttpComponentRouteTest extends AbstractComponentRouteTest {
    private final String PAYLOAD = "<Document type=\"UPDATE\" code=\"4443\">\n" +
            "<Points>11</Points>\n" +
            "<Assists>4</Assists>\n" +
            "</Document>";


    @Override
    protected void sendPayload(final ProducerTemplate producerTemplate) {
        producerTemplate.sendBody(PAYLOAD);
    }

    @Override
    protected void assertMock(final MockEndpoint mockEndpoint) {
        mockEndpoint.expectedMessageCount(1);
        mockEndpoint.message(0).body(String.class).isEqualTo(createResponsePayload());
    }

    private String createResponsePayload() {
        return PAYLOAD;
    }

    @Override
    protected String getEndpointToTest() {
        return CommonConstants.Route.HTTP_INPUT.getId();
    }

    @Override
    protected void configureAdvice(final AdviceWithRouteBuilder adviceWithRouteBuilder) {
        adviceWithRouteBuilder.weaveByToUri("*").replace().to(MOCK_ENDPOINT);
    }
}
