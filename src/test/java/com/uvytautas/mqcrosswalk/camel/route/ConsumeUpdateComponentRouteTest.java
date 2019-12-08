package com.uvytautas.mqcrosswalk.camel.route;

import com.uvytautas.mqcrosswalk.camel.util.CommonConstants;
import com.uvytautas.mqcrosswalk.domain.Document;
import com.uvytautas.mqcrosswalk.repositories.DocumentRepository;
import java.util.HashMap;
import java.util.Map;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.NONE,
        classes = ConsumeUpdateComponentRouteTest.class
)
@DirtiesContext
@TestConfiguration
public class ConsumeUpdateComponentRouteTest extends AbstractComponentRouteTest {

    @Autowired
    private DocumentRepository documentRepository;

    private final String PAYLOAD = "<Document type=\"MASTER\" code=\"4443\">\n" +
            "<Points>11</Points>\n" +
            "<Assists>4</Assists>\n" +
            "</Document>";

    @Override
    protected void sendPayload(final ProducerTemplate producerTemplate) {

        documentRepository.save(new Document("4443", "<BODY></BODY>"));
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
