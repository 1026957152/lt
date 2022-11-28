package com.lt.dom.repository;

import com.lt.dom.oct.LineItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LineItemRepository extends JpaRepository<LineItem
			, Long> {


	List<LineItem> findAllByBooking(long id);
}