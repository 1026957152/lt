package com.lt.dom.repository;

import com.lt.dom.oct.Privilege;
import com.lt.dom.oct.ShippingRate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShippingRateRepository extends JpaRepository<ShippingRate
			, Long> {


	List<ShippingRate> findAllBySupplier(long id);
}