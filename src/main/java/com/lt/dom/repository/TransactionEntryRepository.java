package com.lt.dom.repository;

import com.lt.dom.oct.TransactionEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionEntryRepository extends JpaRepository<TransactionEntry
			, Long> {


	List<TransactionEntry> findByBalance(long id);
}