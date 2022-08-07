package com.lt.dom.repository;

import com.lt.dom.oct.CampaignAssignToTourProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CampaignAssignToReservationRepository extends JpaRepository<CampaignAssignToTourProduct
			, Long> {


    List<CampaignAssignToTourProduct> findByTourId(Long raletiveId);

}