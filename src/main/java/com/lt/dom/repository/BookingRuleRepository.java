package com.lt.dom.repository;

import com.lt.dom.oct.BookingRule;
import com.lt.dom.oct.ComponentRight;
import com.lt.dom.oct.ValidatorGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRuleRepository extends JpaRepository<BookingRule
			, Long> {


    List<BookingRule> findByProductId(long id);
}