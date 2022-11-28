package com.lt.dom.repository;

import com.lt.dom.oct.Place;
import com.lt.dom.oct.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRepository extends JpaRepository<Place
			, Long> {


}