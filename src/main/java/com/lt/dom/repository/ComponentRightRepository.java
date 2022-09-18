package com.lt.dom.repository;

import com.lt.dom.oct.ComponentRight;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComponentRightRepository extends JpaRepository<ComponentRight
			, Long> {

    Page findAllBySupplier(long id, Pageable pageable);

    List<ComponentRight> findAllBySupplier(long id);
}