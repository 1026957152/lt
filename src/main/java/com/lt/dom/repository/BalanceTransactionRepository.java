package com.lt.dom.repository;

import com.lt.dom.oct.BalanceTransaction;
import com.lt.dom.oct.BookingRule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BalanceTransactionRepository extends JpaRepository<BalanceTransaction
			, Long> {



}