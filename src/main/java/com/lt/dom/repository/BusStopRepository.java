package com.lt.dom.repository;

import com.lt.dom.oct.BusStop;
import com.lt.dom.oct.Stop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusStopRepository extends JpaRepository<BusStop
			, Long> {


	Page<BusStop> findAllBySupplier(Pageable pageable, long id);
}