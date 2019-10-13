package com.uvytautas.mqcrosswalk.repositories;

import com.uvytautas.mqcrosswalk.domain.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, Long> {

    Document findByDocumentCode(String documentCode);
}
