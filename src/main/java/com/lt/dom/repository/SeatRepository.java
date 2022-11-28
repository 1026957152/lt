package com.lt.dom.repository;

import com.lt.dom.oct.Seat;
import com.lt.dom.oct.SeatingLayout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat
			, Long> {


	List<Seat> findBySeatingLayoutId(long id);

	List<Seat> findAllByZone(long id);
}