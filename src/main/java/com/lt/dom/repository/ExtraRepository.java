package com.lt.dom.repository;

import com.lt.dom.oct.Extra;
import com.lt.dom.oct.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExtraRepository extends JpaRepository<Extra
			, Long> {

	Page<Extra> findAllBySupplier(Pageable pageable, long id);

    List<Extra> findAllBySupplier(long supplierId);
}