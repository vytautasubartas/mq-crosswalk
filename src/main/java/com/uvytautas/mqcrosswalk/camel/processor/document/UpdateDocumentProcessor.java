package com.uvytautas.mqcrosswalk.camel.processor.document;

import com.uvytautas.mqcrosswalk.camel.exception.DocumentTypeNotFoundException;
import com.uvytautas.mqcrosswalk.camel.util.CommonConstants;
import com.uvytautas.mqcrosswalk.domain.Document;
import com.uvytautas.mqcrosswalk.repositories.DocumentRepository;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import java.util.Optional;

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

        Optional<Document> document = documentRepository.findByDocumentCode(docCode);

        Document doc = document.orElseThrow(() -> new DocumentTypeNotFoundException("error.doc.not-found", docCode));

        doc.setDocumentBody(message.getBody(String.class));
        documentRepository.save(doc);
    }
}
