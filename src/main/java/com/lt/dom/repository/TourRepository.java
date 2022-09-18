package com.lt.dom.repository;

import com.lt.dom.oct.AccessValidator;
import com.lt.dom.oct.Tour;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface TourRepository extends JpaRepository<Tour
			, Long> {


    Optional<Tour> findByProduct(long id);

    List<Tour> findAllByProductIn(Set<Long> collect);
}