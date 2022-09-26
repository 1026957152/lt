package com.lt.dom.repository;

import com.lt.dom.oct.Component;
import com.lt.dom.oct.ComponentRight;
import com.lt.dom.otcenum.EnumBillRecurringInterval;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ComponentRepository extends JpaRepository<Component
			, Long> {

	Optional<Component> findByProductAndComponentRightId(long id, long id1);

    List<Component> findAllByProduct(Long productId);

    Page<Component> findAllByProduct(long id, Pageable pageable);

    List<Component> findAllByProductIn(List<Long> collect);

    List<Component> findAllByBillRecurringInterval(EnumBillRecurringInterval day);

    List<Component> findAllBySubscription(long id);

}