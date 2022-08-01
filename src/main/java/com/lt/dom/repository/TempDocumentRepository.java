package com.lt.dom.repository;

import com.lt.dom.oct.AccessValidator;
import com.lt.dom.oct.TempDocument;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TempDocumentRepository extends JpaRepository<TempDocument
			, Long> {


	Optional<TempDocument> findByCode(String document_id);

	List<TempDocument> findAllByCodeIn(List<String> contract_files);
}