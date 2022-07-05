package com.lt.dom.repository;

import com.lt.dom.oct.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User
			, Long> {



}