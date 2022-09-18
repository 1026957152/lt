package com.lt.dom.repository;

import com.lt.dom.oct.BookingProduct;
import com.lt.dom.oct.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookingProductRepository extends JpaRepository<BookingProduct
			, Long> {


}