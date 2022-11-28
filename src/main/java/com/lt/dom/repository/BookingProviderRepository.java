package com.lt.dom.repository;

import com.lt.dom.oct.BookingProvider;
import com.lt.dom.oct.BookingService;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingProviderRepository extends JpaRepository<BookingProvider
			, Long> {


}