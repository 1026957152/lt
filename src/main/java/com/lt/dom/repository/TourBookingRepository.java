package com.lt.dom.repository;

import com.lt.dom.oct.AccessValidator;
import com.lt.dom.oct.TourBooking;
import com.lt.dom.oct.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface TourBookingRepository extends JpaRepository<TourBooking
			, Long>  , JpaSpecificationExecutor<TourBooking> {


    Optional<TourBooking> findByCode(String code);
}