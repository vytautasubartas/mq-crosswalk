package com.uvytautas.mqcrosswalk.camel.util;

public class CommonConstants {
    private CommonConstants() {
    }

    public static final String DOCUMENT_CODE_HEADER = "documentCode";
    public static final String DOCUMENT_TYPE_HEADER = "documentType";

    public enum Route {
        DOCUMENT_UPDATE("direct:update"),
        DOCUMENT_MASTER("direct:master"),
        DOCUMENT_GATEWAY("uri:documentGateway"),
        ACTIVEMQ_OUTPUT("activemq:queue:ibmmqconsumer"),
        HTTP_INPUT("netty4-http:http://0.0.0.0:9999/input"),
        HEALTH_CHECK("netty4-http:http://0.0.0.0:9999/healthcheck"),
        IBMMQ_INPUT("ibmmq:queue:DEV.QUEUE.1");


        private final String uri;

        Route(String uri) {
            this.uri = uri;
        }

        public String getUri() {
            return uri;
        }
    }
}
