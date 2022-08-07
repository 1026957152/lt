package com.lt.dom.repository;

import com.lt.dom.oct.BalanceTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<BalanceTransaction
			, Long> {


	List<BalanceTransaction> findByBalance(long id);
}