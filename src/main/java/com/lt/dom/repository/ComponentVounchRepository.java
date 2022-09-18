package com.lt.dom.repository;

import com.lt.dom.oct.ComponentVounch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComponentVounchRepository extends JpaRepository<ComponentVounch
			, Long> {

	List<ComponentVounch> findByComponentRight(long id);

	List<ComponentVounch> findByVoucherId(long id);

    List<ComponentVounch> findAllByUser(long id);

    Page<ComponentVounch> findAllByUser(long id, Pageable pageable);
}