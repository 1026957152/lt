package com.lt.dom.repository;

import com.lt.dom.oct.AccessValidator;
import com.lt.dom.oct.WayPoint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WayPointRepository extends JpaRepository<WayPoint
			, Long> {



}