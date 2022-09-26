package com.lt.dom.repository;

import com.lt.dom.oct.BookingProductFuck;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingProductRepository extends JpaRepository<BookingProductFuck
			, Long> {


	List<BookingProductFuck> findAllByBooking(long id);
}