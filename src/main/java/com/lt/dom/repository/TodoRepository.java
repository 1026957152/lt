package com.lt.dom.repository;

import com.lt.dom.oct.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo
			, Long> {

}