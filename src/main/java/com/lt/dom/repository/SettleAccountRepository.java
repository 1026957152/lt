package com.lt.dom.repository;

import com.lt.dom.domain.SettleAccount;
import com.lt.dom.oct.BookingRule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SettleAccountRepository extends JpaRepository<SettleAccount
			, Long> {


}