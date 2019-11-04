package com.uvytautas.mqcrosswalk.camel.processor.header;

import com.uvytautas.mqcrosswalk.camel.util.CommonConstants;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;

public class PayloadHeaderProcessorTest {

    private Processor processor;

    @Before
    public void setUp() {
        processor = new PayloadHeaderProcessor();
    }

    @Test
    public void process() throws Exception {
        final Exchange exchange = Mockito.mock(Exchange.class);
        final Message message = Mockito.mock(Message.class);
        Mockito.when(message.getBody(ByteArrayInputStream.class)).thenReturn(new ByteArrayInputStream("<DOCUMENT code=\"24143\" type=\"MASTER\"></DOCUMENT>".getBytes()));

        Mockito.when(exchange.getMessage()).thenReturn(message);

        processor.process(exchange);

        Mockito.verify(exchange, Mockito.times(1)).getMessage();
        Mockito.verify(message, Mockito.times(1)).setHeader(CommonConstants.DOCUMENT_TYPE_HEADER, "MASTER");
        Mockito.verify(message, Mockito.times(1)).setHeader(CommonConstants.DOCUMENT_CODE_HEADER, "24143");
    }
}