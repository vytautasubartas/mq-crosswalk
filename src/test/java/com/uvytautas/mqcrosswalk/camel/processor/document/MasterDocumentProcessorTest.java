package com.uvytautas.mqcrosswalk.camel.processor.document;

import com.uvytautas.mqcrosswalk.camel.util.CommonConstants;
import com.uvytautas.mqcrosswalk.domain.Document;
import com.uvytautas.mqcrosswalk.repositories.DocumentRepository;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;

import static org.mockito.internal.verification.VerificationModeFactory.times;

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
        ArgumentCaptor<Document> captor = ArgumentCaptor.forClass(Document.class);

        final Exchange exchange = Mockito.mock(Exchange.class);

        Mockito.when(exchange.getMessage()).thenReturn(message);

        processor.process(exchange);
        Mockito.verify(documentRepository, times(1))
                .save(captor.capture());
        Document arg = captor.getValue();

        Assertions.assertEquals("fffff", arg.getDocumentCode());
        Assertions.assertEquals("<BODY></BODY>", arg.getDocumentBody());
    }

    @Test
    public void exceptionWhenNoHeaders() throws Exception {
        final Message message = Mockito.mock(Message.class);

        Mockito.when(message.getBody(String.class)).thenReturn("<BODY></BODY>");

        final Exchange exchange = Mockito.mock(Exchange.class);

        Mockito.when(exchange.getMessage()).thenReturn(message);

        Assertions.assertThrows(NullPointerException.class, () -> processor.process(exchange));
    }

}