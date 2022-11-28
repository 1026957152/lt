package com.lt.dom.repository;

import com.lt.dom.oct.PartnerShareRatePlan;
import com.lt.dom.oct.ShippingRate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ShippingRatePlanRepository extends JpaRepository<ShippingRate
			, Long> {

}