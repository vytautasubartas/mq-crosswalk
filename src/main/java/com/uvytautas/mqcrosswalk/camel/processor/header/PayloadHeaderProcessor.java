package com.uvytautas.mqcrosswalk.camel.processor.header;

import com.uvytautas.mqcrosswalk.camel.util.CommonConstants;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import java.io.ByteArrayInputStream;
import java.util.function.Supplier;

@Component
public class PayloadHeaderProcessor implements Processor {
    private final Supplier<DocumentBuilder> documentBuilderSupplier;

    public PayloadHeaderProcessor(final Supplier<DocumentBuilder> documentBuilderSupplier) {
        this.documentBuilderSupplier = documentBuilderSupplier;
    }

    @Override
    public void process(Exchange exchange) throws Exception {

        Message message = exchange.getMessage();

        byte[] payloadBody = message.getBody(byte[].class);

        DocumentBuilder builder = documentBuilderSupplier.get();
        Document document = builder.parse(new ByteArrayInputStream(payloadBody));
        String documentCode = document.getDocumentElement().getAttribute("code");
        String documentType = document.getDocumentElement().getAttribute("type");

        message.setHeader(CommonConstants.DOCUMENT_CODE_HEADER, documentCode);
        message.setHeader(CommonConstants.DOCUMENT_TYPE_HEADER, documentType);
    }
}
