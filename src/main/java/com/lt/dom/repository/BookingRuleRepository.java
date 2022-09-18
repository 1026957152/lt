package com.lt.dom.repository;

import com.lt.dom.oct.BookingRule;
import com.lt.dom.oct.ComponentRight;
import com.lt.dom.oct.Supplier;
import com.lt.dom.oct.ValidatorGroup;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface BookingRuleRepository extends JpaRepository<BookingRule
			, Long>, JpaSpecificationExecutor<BookingRule> {


    List<BookingRule> findByProduct(long id);

    void deleteAllByProduct(long id);

    Page findByProduct(long id, Pageable pageable);
}