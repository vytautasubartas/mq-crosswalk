package com.uvytautas.mqcrosswalk.repositories;

import com.uvytautas.mqcrosswalk.domain.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DocumentRepository extends JpaRepository<Document, Long> {

    Optional<Document> findByDocumentCode(String documentCode);
}
