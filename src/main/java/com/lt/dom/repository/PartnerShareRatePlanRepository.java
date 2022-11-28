package com.lt.dom.repository;

import com.lt.dom.oct.Activity;
import com.lt.dom.oct.Partner;
import com.lt.dom.oct.PartnerShareRatePlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PartnerShareRatePlanRepository extends JpaRepository<PartnerShareRatePlan
			, Long> {

	List<PartnerShareRatePlan> findAllByPartner(long id);

	Optional<PartnerShareRatePlan> findAllByPartnerAndProduct(long id, long id1);
}