package com.lt.dom.repository;

import com.lt.dom.oct.RoyaltyRule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoyaltyRuleRepository extends JpaRepository<RoyaltyRule
			, Long> {


    List<RoyaltyRule> findAllBySplitCode(String paymentSplitCode);

    List<RoyaltyRule> findAllByComponentRightIn(List<Long> collect);
}