package com.lt.dom.repository;

import com.lt.dom.oct.Extra;
import com.lt.dom.oct.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExtraRepository extends JpaRepository<Extra
			, Long> {

	Page<Extra> findAllBySupplier(Pageable pageable, long id);
}