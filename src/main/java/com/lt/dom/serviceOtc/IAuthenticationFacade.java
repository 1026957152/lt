package com.lt.dom.serviceOtc;

import com.lt.dom.oct.User;
import com.lt.dom.vo.UserVo;
import org.springframework.security.core.Authentication;

public interface IAuthenticationFacade {
    Authentication getAuthentication();

    User getUser(Authentication authentication);

    UserVo getUserVo(Authentication authentication);
}