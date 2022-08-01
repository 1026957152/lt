package com.lt.dom.repository;

import com.lt.dom.oct.AccessValidator;
import com.lt.dom.oct.Traveler;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TravelerRepository extends JpaRepository<Traveler
			, Long> {


    List<Traveler> findAllByBooking(long id);
}