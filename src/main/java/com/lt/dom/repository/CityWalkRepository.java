package com.lt.dom.repository;

import com.lt.dom.oct.AccessValidator;
import com.lt.dom.oct.CityWalk;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CityWalkRepository extends JpaRepository<CityWalk
			, Long> {


}