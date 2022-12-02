package com.lt.dom.repository;

import com.lt.dom.oct.Address;
import com.lt.dom.oct.ShippingCardAddress;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address
			, Long> {


}