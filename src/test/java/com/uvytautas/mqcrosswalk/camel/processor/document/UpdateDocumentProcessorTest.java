package com.uvytautas.mqcrosswalk.camel.processor.document;

import com.uvytautas.mqcrosswalk.camel.exception.DocumentTypeNotFoundException;
import com.uvytautas.mqcrosswalk.camel.util.CommonConstants;
import com.uvytautas.mqcrosswalk.domain.Document;
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
import java.util.Optional;

public class UpdateDocumentProcessorTest {
    private Processor processor;

    @Mock
    private DocumentRepository documentRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        processor = new UpdateDocumentProcessor(documentRepository);
    }

    @Test
    public void saveWhenDocFound() throws Exception {
        final Message message = Mockito.mock(Message.class);

        HashMap headers = Mockito.mock(HashMap.class);
        Mockito.when(headers.get(CommonConstants.DOCUMENT_CODE_HEADER)).thenReturn("fffff");


        Mockito.when(message.getHeaders()).thenReturn(headers);

        Mockito.when(message.getBody(String.class)).thenReturn("<BODY></BODY>");

        Mockito.when(documentRepository.findByDocumentCode("fffff")).thenReturn(Optional.of(new Document("fffff", "body")));
        final Exchange exchange = Mockito.mock(Exchange.class);

        Mockito.when(exchange.getMessage()).thenReturn(message);

        processor.process(exchange);

    }

    @Test(expected = DocumentTypeNotFoundException.class)
    public void exceptionWhenDocNotFound() throws Exception {
        final Message message = Mockito.mock(Message.class);

        HashMap headers = Mockito.mock(HashMap.class);
        Mockito.when(headers.get(CommonConstants.DOCUMENT_CODE_HEADER)).thenReturn("zzzzz");


        Mockito.when(message.getHeaders()).thenReturn(headers);

        Mockito.when(message.getBody(String.class)).thenReturn("<BODY></BODY>");

        final Exchange exchange = Mockito.mock(Exchange.class);

        Mockito.when(exchange.getMessage()).thenReturn(message);

        processor.process(exchange);
    }
}