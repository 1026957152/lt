package com.lt.dom.repository;

import com.lt.dom.oct.GuideInchargeBooking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InchargeBookingRepository extends JpaRepository<GuideInchargeBooking
			, Long> {


	Page<GuideInchargeBooking> findAllByGuideId(long guide_id, Pageable of);
}