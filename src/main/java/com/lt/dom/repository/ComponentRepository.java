package com.lt.dom.repository;

import com.lt.dom.oct.Component;
import com.lt.dom.oct.ComponentRight;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ComponentRepository extends JpaRepository<Component
			, Long> {

	Optional<Component> findByProductIdAndComponentRightId(long id, long id1);

    List<Component> findAllByProductId(Long productId);

    Page<Component> findAllByProductId(long id, Pageable pageable);
}