package com.lt.dom.repository;

import com.lt.dom.oct.ApplicationFee;
import com.lt.dom.oct.Refund;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RefundRepository extends JpaRepository<Refund
			, Long> {


    Optional<Refund> findByCode(String out_refund_no);

    List<Refund> findAllByChargeIn(List<Long> collect);

}