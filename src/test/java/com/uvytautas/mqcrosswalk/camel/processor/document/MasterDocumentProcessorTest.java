package com.uvytautas.mqcrosswalk.camel.processor.document;

import com.uvytautas.mqcrosswalk.camel.util.CommonConstants;
import com.uvytautas.mqcrosswalk.repositories.DocumentRepository;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;

public class MasterDocumentProcessorTest {

    private Processor processor;

    @Mock
    private DocumentRepository documentRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        processor = new MasterDocumentProcessor(documentRepository);
    }

    @Test
    public void saveWhenMessageReceived() throws Exception {
        final Message message = Mockito.mock(Message.class);

        HashMap headers = Mockito.mock(HashMap.class);
        Mockito.when(headers.get(CommonConstants.DOCUMENT_CODE_HEADER)).thenReturn("fffff");


        Mockito.when(message.getHeaders()).thenReturn(headers);

        Mockito.when(message.getBody(String.class)).thenReturn("<BODY></BODY>");


        final Exchange exchange = Mockito.mock(Exchange.class);

        Mockito.when(exchange.getMessage()).thenReturn(message);

        processor.process(exchange);

    }

    @Test(expected = Exception.class)
    public void exceptionWhenNoHeaders() throws Exception {
        final Message message = Mockito.mock(Message.class);


        Mockito.when(message.getBody(String.class)).thenReturn("<BODY></BODY>");


        final Exchange exchange = Mockito.mock(Exchange.class);

        Mockito.when(exchange.getMessage()).thenReturn(message);

        processor.process(exchange);

    }

}