package com.lt.dom.repository;

import com.lt.dom.oct.AccessValidator;
import com.lt.dom.oct.TourBooking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TourBookingRepository extends JpaRepository<TourBooking
			, Long> {



}