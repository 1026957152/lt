package com.lt.dom.repository;

import com.lt.dom.oct.Blog;
import com.lt.dom.oct.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Blog
			, Long> {



}