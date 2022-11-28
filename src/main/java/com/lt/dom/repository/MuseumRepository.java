package com.lt.dom.repository;

import com.lt.dom.oct.Museum;
import com.lt.dom.oct.Showtime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MuseumRepository extends JpaRepository<Museum
			, Long> {


}