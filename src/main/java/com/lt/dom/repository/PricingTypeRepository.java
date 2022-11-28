package com.lt.dom.repository;

import com.lt.dom.oct.AccessValidator;
import com.lt.dom.oct.PricingType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PricingTypeRepository extends JpaRepository<PricingType
			, Long> {

    List<PricingType> findByProductId(long id);

    List<PricingType> findByProductIdIn(List<Long> collect);

    void deleteAllByProductId(long id);
}