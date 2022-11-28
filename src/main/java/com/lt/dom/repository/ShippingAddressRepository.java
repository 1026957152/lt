package com.lt.dom.repository;

import com.lt.dom.oct.Attribute;
import com.lt.dom.oct.ShippingCardAddress;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShippingAddressRepository extends JpaRepository<ShippingCardAddress
			, Long> {


    List<ShippingCardAddress> findAllBySupplier(long id);

    List<ShippingCardAddress> findAllByUser(long id);

    Page<ShippingCardAddress> findAllByUser(long id, Pageable pageable);
}