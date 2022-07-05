package com.lt.dom.repository;

import com.lt.dom.oct.ComponentRight;
import com.lt.dom.oct.Product;
import com.lt.dom.oct.ValidatorGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product
			, Long> {


}