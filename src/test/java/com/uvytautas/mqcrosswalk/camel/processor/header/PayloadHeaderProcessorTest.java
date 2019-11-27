package com.uvytautas.mqcrosswalk.camel.processor.header;

import com.uvytautas.mqcrosswalk.camel.util.CommonConstants;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.util.function.Supplier;

public class PayloadHeaderProcessorTest {

    private Processor processor;

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() throws ParserConfigurationException {
        Supplier<DocumentBuilder> documentBuilderSupplier = (Supplier<DocumentBuilder>) Mockito.mock(Supplier.class);
        Mockito.when(documentBuilderSupplier.get()).thenReturn(DocumentBuilderFactory.newInstance().newDocumentBuilder());
        processor = new PayloadHeaderProcessor(documentBuilderSupplier);
    }

    @Test
    public void process() throws Exception {
        final Exchange exchange = Mockito.mock(Exchange.class);
        final Message message = Mockito.mock(Message.class);
        Mockito.when(message.getBody(byte[].class)).thenReturn("<DOCUMENT code=\"24143\" type=\"MASTER\"></DOCUMENT>".getBytes());

        Mockito.when(exchange.getMessage()).thenReturn(message);

        processor.process(exchange);

        Mockito.verify(exchange, Mockito.times(1)).getMessage();
        Mockito.verify(message, Mockito.times(1)).setHeader(CommonConstants.DOCUMENT_TYPE_HEADER, "MASTER");
        Mockito.verify(message, Mockito.times(1)).setHeader(CommonConstants.DOCUMENT_CODE_HEADER, "24143");
    }
}