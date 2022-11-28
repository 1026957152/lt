package com.lt.dom.repository;

import com.lt.dom.oct.BookingRule;
import com.lt.dom.oct.Cancellation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface CancellationRepository extends JpaRepository<Cancellation
			, Long>, JpaSpecificationExecutor<Cancellation> {


    List<Cancellation> findAllByBooking(long id);
}