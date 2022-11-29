package com.lt.dom.repository;

import com.lt.dom.oct.Place;
import com.lt.dom.oct.Trip;
import com.lt.dom.otcenum.EnumPlaceTyp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlaceRepository extends JpaRepository<Place
			, Long> {


    List<Place> findAllByType(EnumPlaceTyp region);

}