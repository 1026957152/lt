package com.lt.dom.repository;

import com.lt.dom.oct.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment
			, Long> {



}