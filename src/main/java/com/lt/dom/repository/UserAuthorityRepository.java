package com.lt.dom.repository;

import com.lt.dom.oct.AccessValidator;
import com.lt.dom.oct.Openid;
import com.lt.dom.oct.UserAuthority;
import com.lt.dom.otcenum.EnumIdentityType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public interface UserAuthorityRepository extends JpaRepository<UserAuthority
			, Long> {


    Optional<UserAuthority> findByIdentityTypeAndIdentifier(EnumIdentityType enumIdentityType, String id_);

    void deleteAllByIdentityTypeAndIdentifier(EnumIdentityType weixin, String openid);

    Optional<UserAuthority> findByIdentityTypeAndIdentifierAndUserId(EnumIdentityType weixin, String openid, Long id);

    Optional<UserAuthority> findByIdentityTypeAndUserId(EnumIdentityType weixin, Long id);

    List<UserAuthority> findByUserIdIn(List<Long> collect);
}