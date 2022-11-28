package com.lt.dom.repository;

import com.lt.dom.oct.Invoice;
import com.lt.dom.oct.Usage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice
			, Long> {



}