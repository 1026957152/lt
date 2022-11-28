package com.lt.dom.repository;

import com.lt.dom.oct.CrossSell;
import com.lt.dom.oct.Extra;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CrossSellRepository extends JpaRepository<CrossSell
			, Long> {

	Page<CrossSell> findAllBySupplier(Pageable pageable, long id);
}