package com.lt.dom.repository;

import com.lt.dom.oct.BusRoute;
import com.lt.dom.oct.Stop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusRouteRepository extends JpaRepository<BusRoute
			, Long> {


	Page<BusRoute> findAllBySupplier(Pageable pageable, long id);
}