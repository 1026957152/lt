package com.lt.dom.repository;

import com.lt.dom.oct.BusRoute;
import com.lt.dom.oct.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car
			, Long> {


}