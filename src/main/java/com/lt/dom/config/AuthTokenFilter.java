package com.lt.dom.config;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.lt.dom.JwtUtils;
import com.lt.dom.error.ApiErrorResponse;
import com.lt.dom.otcenum.EnumRedemptionfailures;
import com.lt.dom.vo.LogVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

public class AuthTokenFilter extends OncePerRequestFilter {
	@Autowired
	private JwtUtils jwtUtils;
	@Autowired
	private MyUserDetailsService userDetailsService;


	//此处声明异常全局处理
	@Autowired
	private HandlerExceptionResolver handlerExceptionResolver;


	private  AuthenticationTokenProvider authenticationTokenProvider;

	private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

	public AuthTokenFilter(AuthenticationTokenProvider authenticationTokenProvider) {

		this.authenticationTokenProvider = authenticationTokenProvider;
	}

/*
	@Override
	public void onAuthenticationFailure(HttpServletRequest httpServletRequest,
										HttpServletResponse httpServletResponse,
										AuthenticationException e)
			throws IOException, ServletException {

		String errorMessage = ExceptionUtils.getMessage(e);

		sendError(httpServletResponse, HttpServletResponse.SC_UNAUTHORIZED, errorMessage, e);
	}
*/

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			String jwt = parseJwt(request);

			System.out.println("-----------------------------"+ jwt);

			if (jwt != null ){
				if(jwtUtils.validateJwtToken(jwt)) {
					String username = jwtUtils.getUserNameFromJwtToken(jwt);
					System.out.println("解剖" + username);
					Gson gson = new Gson();
					LogVo logVo = gson.fromJson(username, LogVo.class);
					if (logVo.getType() == 1) {
						username = logVo.getName();
						UserDetails userDetails = userDetailsService.loadUserByUsername(username);
						UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
								userDetails, null, userDetails.getAuthorities());
						authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
						SecurityContextHolder.getContext().setAuthentication(authentication);

					} else {
						String token = logVo.getName();
						Authentication authentication = authenticationTokenProvider.authenticate(
								new AuthenticationToken(token, Collections.emptyList()));


/*
					UserDetails userDetails = userDetailsService.loadUserByUsername(username);
					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());*/
						//authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
						SecurityContextHolder.getContext().setAuthentication(authentication);
					}

				}else{


					throw new BadCredentialsException("Invalid JWT token: Unable to read JSON value");
				}

			}

		} catch (Exception e) {

			e.printStackTrace();
			System.out.println("ddddddd错误错误错误错误错误错误错误错误错误错误错误错误错误错误错误错误ddddddddddddddddddddd错误错误ddddddddddddd");
			logger.error("Cannot set user authentication: {}", e);

			// custom error response class used across my project

			handlerExceptionResolver.resolveException(request, response, null, e);
			return;

/*			ApiErrorResponse response__ =
					new ApiErrorResponse(HttpStatus.UNAUTHORIZED, EnumRedemptionfailures.resource_not_found.name(),
							"认证我的一场 ",e.getLocalizedMessage());
			//response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			response.getWriter().write(convertObjectToJson(response__));
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);

			response.setStatus(HttpStatus.FORBIDDEN.value());
			return;*/
		}
		filterChain.doFilter(request, response);
	}

/*	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException
	{
		// Populate excludeUrlPatterns on which one to exclude here

		return false;
	}*/

	private String parseJwt(HttpServletRequest request) {
		String headerAuth = request.getHeader("Authorization");
		if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
			return headerAuth.substring(7, headerAuth.length());
		}
		return null;
	}
}