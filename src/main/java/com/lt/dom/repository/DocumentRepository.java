package com.lt.dom.repository;

import com.lt.dom.oct.AccessValidator;
import com.lt.dom.oct.Document;
import com.lt.dom.otcenum.EnumDocumentType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DocumentRepository extends JpaRepository<Document
			, Long> {


	List<Document> findAllByRaletiveId(long id);

    Optional<Document> findByCode(String document_id);

    List<Document> findAllByTypeAndRaletiveId(EnumDocumentType scenario_logo, long id);

    List<Document> findAllByTypeInAndRaletiveId(List<EnumDocumentType> scenario_logo, long id);
}