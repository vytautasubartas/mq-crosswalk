package com.uvytautas.mqcrosswalk.camel.exception;

import java.util.Arrays;
import java.util.List;

public class DocumentTypeNotFoundException extends RuntimeException {
private List<Object> variables;
    public DocumentTypeNotFoundException(String message, Object...variables) {
        super(message);
        this.variables = Arrays.asList(variables);
    }
}
