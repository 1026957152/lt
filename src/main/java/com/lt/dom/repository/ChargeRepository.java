package com.lt.dom.repository;

import com.lt.dom.oct.BookingRule;
import com.lt.dom.oct.Charge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChargeRepository extends JpaRepository<Charge
			, Long> {



    Optional<Charge> findByCode(String out_trade_no);

    List<Charge> findByBooking(long id);
}