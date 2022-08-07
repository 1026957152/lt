package com.lt.dom.repository;

import com.lt.dom.oct.BookingQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingQuestionRepository extends JpaRepository<BookingQuestion

		, Long> {


	List<BookingQuestion> findByProductId(long id);
}