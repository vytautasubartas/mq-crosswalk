package com.uvytautas.mqcrosswalk.camel.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

public class CommonConstants {
    private CommonConstants() {
    }

    public static final String DOCUMENT_CODE_HEADER = "documentCode";
    public static final String DOCUMENT_TYPE_HEADER = "documentType";

    public enum Route {
        DOCUMENT_UPDATE("direct:update"),
        DOCUMENT_MASTER("direct:master"),
        DOCUMENT_GATEWAY("direct:documentGateway"),
        ACTIVEMQ_OUTPUT("activemq:queue:ibmmqconsumer"),
        HTTP_INPUT("netty4-http:http://0.0.0.0:9999/input"),
        HEALTH_CHECK("netty4-http:http://0.0.0.0:9999/healthcheck"),
        IBMMQ_INPUT("ibmmq:queue:DEV.QUEUE.1?jmsMessageType=Text");


        private final String uri;
        private String id;

        Route(String uri) {
            this.uri = uri;
            this.id = StringUtils.truncate(DigestUtils.md5DigestAsHex(uri.getBytes()), 10);
        }

        public String getUri() {
            return uri;
        }

        public String getId() {
            return id;
        }
    }

    public enum DocumentTypes {
        MASTER,
        UPDATE
    }
}
