package com.lt.dom.repository;

import com.lt.dom.oct.Component;
import com.lt.dom.oct.ComponentRight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ComponentRepository extends JpaRepository<Component
			, Long> {

	Optional<Component> findByProductIdAndComponentRightId(long id, long id1);
}