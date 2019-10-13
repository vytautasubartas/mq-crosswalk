package com.uvytautas.mqcrosswalk.camel.processor.document;

import com.uvytautas.mqcrosswalk.camel.util.CommonConstants;
import com.uvytautas.mqcrosswalk.domain.Document;
import com.uvytautas.mqcrosswalk.repositories.DocumentRepository;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
public class UpdateDocumentProcessor implements Processor {

    private final DocumentRepository documentRepository;

    public UpdateDocumentProcessor(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    @Override
    public void process(Exchange exchange) {
        Message message = exchange.getMessage();
        String docCode = message.getHeaders().get(CommonConstants.DOCUMENT_CODE_HEADER).toString();

        Document document = documentRepository.findByDocumentCode(docCode);
        document.setDocumentBody(message.getBody(String.class));
        documentRepository.save(document);
    }
}
