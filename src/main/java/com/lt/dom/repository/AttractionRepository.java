package com.lt.dom.repository;

import com.lt.dom.oct.Attraction;
import com.lt.dom.otcenum.EnumPrivacyLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface AttractionRepository extends JpaRepository<Attraction
			, Long>  , JpaSpecificationExecutor<Attraction> {
    List<Attraction> findAllBySupplier(long id);

    List<Attraction> findAllByIdInAndPrivacyLevel(List<Long> sortList, EnumPrivacyLevel public_);

    List<Attraction> findAllByPrivacyLevel(EnumPrivacyLevel public_);


//	List<Attraction> findByTouristAttractionId(long tourist_attraction_id);

}