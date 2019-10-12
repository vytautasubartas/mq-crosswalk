package com.uvytautas.mqcrosswalk.camel.processor.document;

import com.uvytautas.mqcrosswalk.camel.util.CommonConstants;
import com.uvytautas.mqcrosswalk.domain.Document;
import com.uvytautas.mqcrosswalk.repositories.DocumentRepository;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
public class MasterDocumentProcessor implements Processor {

    private final DocumentRepository documentRepository;

    public MasterDocumentProcessor(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    @Override
    public void process(Exchange exchange) {

        Message message = exchange.getMessage();
        documentRepository.save(new Document(message.getHeaders().get(CommonConstants.DOCUMENT_CODE_HEADER).toString(), message.getBody(String.class)));

    }
}
