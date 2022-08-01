package com.lt.dom.repository;

import com.lt.dom.oct.ComponentVounch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComponentVounchRepository extends JpaRepository<ComponentVounch
			, Long> {

	List<ComponentVounch> findByComponentRightId(long id);

	List<ComponentVounch> findByVoucherId(long id);
}