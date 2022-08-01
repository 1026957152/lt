package com.lt.dom.repository;

import com.lt.dom.oct.Balance;
import com.lt.dom.oct.BalanceSettlement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BalanceRepository extends JpaRepository<Balance
			, Long> {



}