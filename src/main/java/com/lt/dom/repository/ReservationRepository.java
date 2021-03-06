package com.lt.dom.repository;

import com.lt.dom.oct.BookingRule;
import com.lt.dom.oct.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation
			, Long> {

}