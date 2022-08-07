package com.lt.dom.repository;

import com.lt.dom.oct.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User
			, Long> {


    Optional<User> findByUsernameAndPassword(String user_name, String user_password);

    Optional<User> findByUsername(String username);

    Optional<User> findByPhone(String phone);

    Optional<User> findByEmail(String email);

    Optional<User> findByRealNameAndIdCard(String real_name, String id_card);
}