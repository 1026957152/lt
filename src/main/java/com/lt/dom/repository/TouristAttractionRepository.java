package com.lt.dom.repository;

import com.lt.dom.oct.Attraction;
import com.lt.dom.oct.TouristAttraction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TouristAttractionRepository extends JpaRepository<TouristAttraction
			, Long> {



}