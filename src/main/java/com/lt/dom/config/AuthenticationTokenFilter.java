package com.lt.dom.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationTokenFilter extends AbstractAuthenticationProcessingFilter {

  private String authHeaderName = "bizi-auth-token";

  public final static String LOGIN_URL = "/login/authToken";

  public AuthenticationTokenFilter() {
    super(new AntPathRequestMatcher(LOGIN_URL));
  }

  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
    String token = request.getHeader(authHeaderName);
    AuthenticationToken authRequest = new AuthenticationToken(token);
    authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    return this.getAuthenticationManager().authenticate(authRequest);
  }
}