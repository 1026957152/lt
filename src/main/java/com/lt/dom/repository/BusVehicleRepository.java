package com.lt.dom.repository;

import com.lt.dom.oct.BusStop;
import com.lt.dom.oct.BusVehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusVehicleRepository extends JpaRepository<BusVehicle
			, Long> {


	Page<BusVehicle> findAllBySupplier(Pageable pageable, long id);
}