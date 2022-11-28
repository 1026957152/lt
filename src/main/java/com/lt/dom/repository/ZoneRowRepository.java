package com.lt.dom.repository;

import com.lt.dom.oct.Seat;
import com.lt.dom.oct.ZoneRow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ZoneRowRepository extends JpaRepository<ZoneRow, Long> {



	List<ZoneRow> findAllByZone(long id);
}