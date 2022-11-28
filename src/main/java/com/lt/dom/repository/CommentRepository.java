package com.lt.dom.repository;

import com.lt.dom.oct.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment
			, Long> {


    List<Comment> findAllByProduct(long id);

}