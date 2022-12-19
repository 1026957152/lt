package com.lt.dom;

import com.lt.dom.oct.Activity;
import com.lt.dom.oct.Fulfilled_item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FulfiiledItemRepository extends JpaRepository<Fulfilled_item
			, Long> {

	List<Fulfilled_item> findAllByBooking(Long id);
}