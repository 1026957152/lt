package com.lt.dom.repository;

import com.lt.dom.oct.BusStop;
import com.lt.dom.oct.PolylinePoint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PolylienPointRepository extends JpaRepository<PolylinePoint
			, Long> {



}