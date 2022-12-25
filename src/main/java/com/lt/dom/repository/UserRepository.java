package com.lt.dom.repository;

import com.lt.dom.oct.Supplier;
import com.lt.dom.oct.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User
			, Long>  , JpaSpecificationExecutor<User> {


    Optional<User> findByUsernameAndPassword(String user_name, String user_password);

    Optional<User> findByUsername(String username);

    Optional<User> findByPhone(String phone);



    Optional<User> findByRealNameAndIdCardAndPhoneAndEnabled(String real_name, String id_card, String phone, boolean b);

    Optional<User> findByPhoneAndEnabled(String s, boolean b);

    Optional<User> findByRealNameAndIdCardAndEnabled(String real_name, String id_card, boolean b);

    User findByEmail(String email);


    Optional<User> findByCode(String credential);


}