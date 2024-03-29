package com.lt.dom.repository;

import com.lt.dom.oct.AccessValidator;
import com.lt.dom.oct.RatePlan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatePlanRepository extends JpaRepository<RatePlan
			, Long> {

    Page<RatePlan> findAllByProduct(long id, Pageable pageable);

    List<RatePlan> findAllByProduct(long id);
}