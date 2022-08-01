package com.lt.dom.repository;

import com.lt.dom.oct.AccessValidator;
import com.lt.dom.oct.Export;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExportRepository extends JpaRepository<Export
			, Long> {


}