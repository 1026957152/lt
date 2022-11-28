package com.lt.dom.repository;

import com.lt.dom.oct.Attraction;
import com.lt.dom.oct.SeatingLayout;
import com.lt.dom.oct.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatingLayoutRepository extends JpaRepository<SeatingLayout
			, Long> {


    Page<SeatingLayout> findAllBySupplier(Supplier supplier, Pageable pageable);
}