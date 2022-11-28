package com.lt.dom.repository;

import com.lt.dom.oct.PassProduct;
import com.lt.dom.oct.Product;
import com.lt.dom.otcenum.EnumProductType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PassProductRepository extends JpaRepository<PassProduct
			, Long> {


    Optional<PassProduct> findByProduct(long id);
}