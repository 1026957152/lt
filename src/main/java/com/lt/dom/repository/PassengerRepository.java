package com.lt.dom.repository;

import com.lt.dom.credit._信合_信用等级信息;
import com.lt.dom.oct.Passenger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassengerRepository extends JpaRepository<Passenger
			, Long> {

	Page<Passenger> findAllByUser(long id, Pageable pageable);
}