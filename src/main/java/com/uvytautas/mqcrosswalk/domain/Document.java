package com.uvytautas.mqcrosswalk.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String documentCode;
    private String documentBody;

    public Document() {
    }

    public Document(String documentCode, String documentBody) {
        this.documentCode = documentCode;
        this.documentBody = documentBody;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocumentCode() {
        return documentCode;
    }

    public void setDocumentCode(String documentCode) {
        this.documentCode = documentCode;
    }

    public String getDocumentBody() {
        return documentBody;
    }

    public void setDocumentBody(String documentBody) {
        this.documentBody = documentBody;
    }
}
