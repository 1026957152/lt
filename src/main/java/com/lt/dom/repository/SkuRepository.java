package com.lt.dom.repository;

import com.lt.dom.oct.MovieProduct;
import com.lt.dom.oct.Sku;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SkuRepository extends JpaRepository<Sku
			, Long> {


    List<Sku> findAllByZoneIn(List<Long> collect);

    void deleteAllByMovieProduct(MovieProduct product);

}