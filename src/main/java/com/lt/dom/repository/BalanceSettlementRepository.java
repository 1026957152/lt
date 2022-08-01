package com.lt.dom.repository;

import com.lt.dom.oct.BalanceSettlement;
import com.lt.dom.oct.BalanceTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BalanceSettlementRepository extends JpaRepository<BalanceSettlement
			, Long> {



}