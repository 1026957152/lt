package com.lt.dom.repository;

import com.lt.dom.oct.Attribute;
import com.lt.dom.oct.Stop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StopRepository extends JpaRepository<Stop
			, Long> {


	Page<Stop> findAllBySupplier(Pageable pageable, long id);
}