package com.lt.dom.repository;

import com.lt.dom.oct.Attraction;
import com.lt.dom.oct.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface RegionRepository extends JpaRepository<Region
			, Long>  , JpaSpecificationExecutor<Attraction> {



}