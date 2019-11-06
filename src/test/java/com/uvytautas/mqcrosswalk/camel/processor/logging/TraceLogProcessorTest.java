package com.uvytautas.mqcrosswalk.camel.processor.logging;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TraceLogProcessorTest {
    //Leader election
    //Rebalansing
    //Hazelcast
    //mutation tests
    //<groupId>org.pitest</groupId> kaip gaming gateway
    private TraceLogProcessor traceLogProcessor;

    @Before
    public void setUp() {
        traceLogProcessor = new TraceLogProcessor();
    }

    @Test
    public void shouldProcessErrorMessage() {
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();
        Logger logger = (Logger) LoggerFactory.getLogger(TraceLogProcessor.class);

        final Exchange exchange = Mockito.mock(Exchange.class);
        final Message message = Mockito.mock(Message.class);

        HashMap headers = Mockito.mock(HashMap.class);
        Mockito.when(headers.toString()).thenReturn("headers:headers");
        Mockito.when(message.getHeaders()).thenReturn(headers);

        Mockito.when(message.getBody(String.class)).thenReturn("<BODY></BODY>");

        Mockito.when(exchange.getMessage()).thenReturn(message);

        logger.addAppender(listAppender);

        traceLogProcessor.process(exchange);

        List<ILoggingEvent> logsList = listAppender.list;
        assertEquals(1, logsList.size());
        assertEquals(String.format("Body: %s%nHeaders: %s", "<BODY></BODY>", "headers:headers"), logsList.get(0)
                .getFormattedMessage());
        assertEquals(Level.INFO, logsList.get(0)
                .getLevel());
    }
}