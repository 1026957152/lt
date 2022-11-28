package com.lt.dom.repository;

import com.lt.dom.oct.Activity;
import com.lt.dom.oct.BusStopPlaceKey;
import com.lt.dom.oct.PlaceRegistration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRegistrationRepository extends JpaRepository<PlaceRegistration
			, BusStopPlaceKey> {

}