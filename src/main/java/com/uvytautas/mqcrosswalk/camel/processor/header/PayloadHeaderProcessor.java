package com.uvytautas.mqcrosswalk.camel.processor.header;

import com.uvytautas.mqcrosswalk.camel.util.CommonConstants;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;

@Component
public class PayloadHeaderProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {

        Message message = exchange.getMessage();

        ByteArrayInputStream payloadBody = message.getBody(ByteArrayInputStream.class);
        payloadBody.reset(); //TODO sort this out
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(payloadBody);
        String documentCode = document.getDocumentElement().getAttribute("code");
        String documentType = document.getDocumentElement().getAttribute("type");

        message.setHeader(CommonConstants.DOCUMENT_CODE_HEADER, documentCode);
        message.setHeader(CommonConstants.DOCUMENT_TYPE_HEADER, documentType);
    }
}
