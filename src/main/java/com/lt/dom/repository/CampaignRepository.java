package com.lt.dom.repository;

import com.lt.dom.oct.Campaign;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CampaignRepository extends JpaRepository<Campaign
			, Long> , JpaSpecificationExecutor<Campaign> {


    Page<Campaign> findAllByScenario(long id, Pageable of);
}