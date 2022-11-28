package com.lt.dom.repository;

import com.lt.dom.oct.Theatre;
import com.lt.dom.oct.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripRepository extends JpaRepository<Trip
			, Long> {


}