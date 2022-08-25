package com.lt.dom.repository;

import com.lt.dom.oct.Request;
import com.lt.dom.oct.Review;
import com.lt.dom.otcenum.EnumRequestType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review
			, Long> {


    List<Review> findAllByRequest(long id);


}