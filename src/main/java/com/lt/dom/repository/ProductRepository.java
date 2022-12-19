package com.lt.dom.repository;

import com.lt.dom.oct.ComponentRight;
import com.lt.dom.oct.Product;
import com.lt.dom.oct.TourBooking;
import com.lt.dom.oct.ValidatorGroup;
import com.lt.dom.otcenum.EnumPrivacyLevel;
import com.lt.dom.otcenum.EnumProductStatus;
import com.lt.dom.otcenum.EnumProductType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product
			, Long> , JpaSpecificationExecutor<Product> {


    Page<Product> findBySupplierId(long id, Pageable pageable);

    Page<Product> findAllByTypeAndTypeTo(EnumProductType attraction, long id, Pageable pageable);

    List<Product> findAllByTypeAndTypeTo(EnumProductType attraction, long id);

    List<Product> findBySupplierId(long id);

    List<Product> findAllByCodeIn(List<String> collect);

    List<Product> findAllByIdIn(List<Long> collect);

    List<Product> findAllByType(EnumProductType pass);

    Optional<Product> findByCode(String item_id);

    List<Product> findAllByStatusAndPrivacyLevel(EnumProductStatus active, EnumPrivacyLevel public_);

    Page<Product> findAllByIdIn(List<Long> list, Pageable of);

    List<Product> findAllBySupplierId(Long id);

}