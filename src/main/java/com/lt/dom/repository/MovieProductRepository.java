package com.lt.dom.repository;

import com.lt.dom.oct.MovieProduct;
import com.lt.dom.oct.PassProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MovieProductRepository extends JpaRepository<MovieProduct
			, Long> {


    Optional<MovieProduct> findByProduct(long id);

    List<MovieProduct> findAllByProductIn(List<Long> collect);

    List<MovieProduct> findByMovie(long id);

    List<MovieProduct> findAllByTheatre(long id);

}