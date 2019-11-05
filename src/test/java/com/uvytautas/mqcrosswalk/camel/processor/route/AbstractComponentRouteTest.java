package com.uvytautas.mqcrosswalk.camel.processor.route;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.spring.boot.CamelContextConfiguration;
import org.apache.commons.lang3.ObjectUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;

@Primary
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public abstract class AbstractComponentRouteTest {
    protected static final String MOCK_ENDPOINT = "mock:response";
    private static final String START_ENDPOINT = "direct:start";

    @Autowired
    private CamelContext camelContext;

    //Do we need configuration

    //Annotations

    @Bean
    CamelContextConfiguration camelContextConfiguration() {
        return new CamelContextConfiguration() {
            @Override
            public void beforeApplicationStart(final CamelContext camelContext) {
                camelContext.getRoutePolicyFactories().clear();
                camelContext.getRouteDefinitions()
                        .stream()
                        .filter(it -> !it.getId().equals(getEndpointToTest()))
                        .forEach(it -> removeRoute(camelContext, it.getId()));

                try {
                    camelContext.getRouteDefinition(getEndpointToTest())
                            .adviceWith(camelContext, new AdviceWithRouteBuilder() {
                                @Override
                                public void configure() {
                                    ObjectUtils.defaultIfNull(getOriginalRoute().getRoutePolicies(), new ArrayList<>()).clear();
                                    replaceFromWith(START_ENDPOINT);
                                    configureAdvice(this);
                                }
                            })
                            .autoStartup(true);
                } catch (Exception e) {
                    throw new IllegalArgumentException("error.not.configured", e);
                }
            }

            @Override
            public void afterApplicationStart(final CamelContext camelContext) {

            }
        };
    }

    private void removeRoute(CamelContext camelContext, String routeId) {
        try {
            camelContext.stopRoute(routeId);
            camelContext.removeRoute(routeId);
        } catch (Exception e) {
            throw new IllegalArgumentException("error.not.configured", e);
        }
    }


    @Test
    public void testRoute() throws InterruptedException {
        MockEndpoint mockEndpoint = (MockEndpoint) camelContext.getEndpoint(MOCK_ENDPOINT);
        ProducerTemplate producerTemplate = camelContext.createProducerTemplate();
        producerTemplate.setDefaultEndpointUri(START_ENDPOINT);

        assertMock(mockEndpoint);

        sendPayload(producerTemplate);

        mockEndpoint.assertIsSatisfied(0L);
    }

    protected abstract void sendPayload(final ProducerTemplate producerTemplate);

    protected abstract void assertMock(final MockEndpoint mockEndpoint);

    protected abstract String getEndpointToTest();

    protected abstract void configureAdvice(final AdviceWithRouteBuilder adviceWithRouteBuilder);

    @AfterEach
    void tearDown() throws Exception {
        camelContext.stop();
    }
}

