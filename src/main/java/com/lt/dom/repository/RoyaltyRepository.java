package com.lt.dom.repository;

import com.lt.dom.oct.BookingRule;
import com.lt.dom.oct.Royalty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoyaltyRepository extends JpaRepository<Royalty
			, Long> {


}