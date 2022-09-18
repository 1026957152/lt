package com.lt.dom.credit;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StepRepository extends JpaRepository<CreditStep
			, Long> {

	List<CreditStep> findAllByRequest(long id);

	List<CreditStep> findAllByRequestIn(List<Long> collect);
}