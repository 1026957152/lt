package com.lt.dom.repository;

import com.lt.dom.oct.AccessValidator;
import com.lt.dom.oct.UserAuthority;
import com.lt.dom.otcenum.EnumIdentityType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserAuthorityRepository extends JpaRepository<UserAuthority
			, Long> {


    Optional<UserAuthority> findByIdentityTypeAndIdentifier(EnumIdentityType enumIdentityType, String id_);

    void deleteAllByIdentityTypeAndIdentifier(EnumIdentityType weixin, String openid);
}