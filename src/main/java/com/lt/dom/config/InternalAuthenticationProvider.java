package com.lt.dom.config;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;

@Component
public class InternalAuthenticationProvider implements AuthenticationProvider {

  @Transactional(Transactional.TxType.REQUIRES_NEW)
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    Assert.isTrue(!authentication.isAuthenticated(), "Already authenticated");
    if (!StringUtils.hasText(authentication.getPrincipal().toString())) {
      throw new InternalAuthenticationServiceException("User key must not be empty.");
    }

    System.out.println("ddddddddddddddddddddddddddddf ufdf sdf ");
    /**TODO do the logic here and return not null authentication object*/

    return null;
  }

  public boolean supports(Class<?> authentication) {
    return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
  }
}