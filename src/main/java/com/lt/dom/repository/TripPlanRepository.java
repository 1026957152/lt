package com.lt.dom.repository;

import com.lt.dom.oct.Trip;
import com.lt.dom.oct.TripPlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripPlanRepository extends JpaRepository<TripPlan
			, Long> {


}