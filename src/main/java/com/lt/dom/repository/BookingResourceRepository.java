package com.lt.dom.repository;

import com.lt.dom.oct.BookingResource;
import com.lt.dom.oct.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface BookingResourceRepository extends JpaRepository<BookingResource
			, Long> , JpaSpecificationExecutor<BookingResource> {


}