package com.lt.dom.repository;

import com.lt.dom.oct.BalanceSettlement;
import com.lt.dom.oct.RoyaltySettlement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoyaltySettlementRepository extends JpaRepository<RoyaltySettlement
			, Long> {



}