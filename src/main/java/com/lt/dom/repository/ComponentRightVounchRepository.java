package com.lt.dom.repository;

import com.lt.dom.oct.ComponentRightVounch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComponentRightVounchRepository extends JpaRepository<ComponentRightVounch
			, Long> {

	List<ComponentRightVounch> findByComponentRightId(long id);

	List<ComponentRightVounch> findByVoucherId(long id);
}