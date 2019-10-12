package com.uvytautas.mqcrosswalk.camel.processor.logging;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TraceLogProcessor implements Processor {

    private static final Logger LOGGER = LoggerFactory.getLogger(TraceLogProcessor.class);

    @Override
    public void process(Exchange exchange) {
        Message message = exchange.getMessage();
        LOGGER.info("Body: {}\nHeaders: {}", message.getBody(String.class), message.getHeaders());
    }
}
