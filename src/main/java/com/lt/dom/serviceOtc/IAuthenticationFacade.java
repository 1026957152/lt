package com.lt.dom.serviceOtc;

import com.lt.dom.oct.User;
import org.springframework.security.core.Authentication;

public interface IAuthenticationFacade {
    Authentication getAuthentication();

    User getUser(Authentication authentication);
}