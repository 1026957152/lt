package com.lt.dom.repository;

import com.lt.dom.oct.PricingRate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PricingTypeRepository extends JpaRepository<PricingRate
			, Long> {

    List<PricingRate> findByProductId(long id);

    List<PricingRate> findByProductIdIn(List<Long> collect);

    void deleteAllByProductId(long id);
}