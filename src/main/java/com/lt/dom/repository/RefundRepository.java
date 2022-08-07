package com.lt.dom.repository;

import com.lt.dom.oct.ApplicationFee;
import com.lt.dom.oct.Refund;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefundRepository extends JpaRepository<Refund
			, Long> {


}