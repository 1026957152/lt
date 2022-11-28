package com.lt.dom.repository;

import com.lt.dom.oct.AccessValidator;
import com.lt.dom.oct.BookingService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingServiceRepository extends JpaRepository<BookingService
			, Long> {


}