package com.lt.dom.repository;

import com.lt.dom.oct.Attraction;
import com.lt.dom.oct.Balance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttractionRepository extends JpaRepository<Attraction
			, Long> {
    List<Attraction> findAllBySupplier(long id);



//	List<Attraction> findByTouristAttractionId(long tourist_attraction_id);

}