package com.lt.dom.repository;

import com.lt.dom.oct.Seat;
import com.lt.dom.oct.Zone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ZoneRepository extends JpaRepository<Zone
			, Long> {


	List<Zone> findBySeatingLayoutId(long id);
}