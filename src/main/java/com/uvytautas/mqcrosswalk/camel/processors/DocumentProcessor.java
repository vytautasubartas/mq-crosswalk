package com.uvytautas.mqcrosswalk.camel.processors;

import com.uvytautas.mqcrosswalk.domain.Document;
import com.uvytautas.mqcrosswalk.repositories.DocumentRepository;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
public class DocumentProcessor implements Processor {

    private final DocumentRepository documentRepository;

    public DocumentProcessor(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    @Override
    public void process(Exchange exchange) {

        Message message = exchange.getMessage();
        documentRepository.save(new Document(message.getMessageId(), message.getBody(String.class)));

    }
}
