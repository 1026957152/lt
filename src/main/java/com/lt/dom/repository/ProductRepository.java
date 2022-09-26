package com.lt.dom.repository;

import com.lt.dom.oct.ComponentRight;
import com.lt.dom.oct.Product;
import com.lt.dom.oct.ValidatorGroup;
import com.lt.dom.otcenum.EnumProductType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product
			, Long> {


    Page<Product> findBySupplierId(long id, Pageable pageable);

    Page<Product> findAllByTypeAndTypeTo(EnumProductType attraction, long id, Pageable pageable);

    List<Product> findAllByTypeAndTypeTo(EnumProductType attraction, long id);

    List<Product> findBySupplierId(long id);

    List<Product> findAllByCodeIn(List<String> collect);

    List<Product> findAllByIdIn(List<Long> collect);

    List<Product> findAllByType(EnumProductType pass);
}