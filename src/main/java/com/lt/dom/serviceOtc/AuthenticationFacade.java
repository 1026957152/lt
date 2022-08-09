package com.lt.dom.serviceOtc;

import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.User;
import com.lt.dom.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
public class AuthenticationFacade implements IAuthenticationFacade {
    @Autowired
    private UserRepository userRepository;

    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    public User getUser(Authentication authentication) {

        if(authentication != null && authentication.isAuthenticated()){
            UserDetails user_ = (UserDetails)authentication.getPrincipal();
            Optional<User> optionalUser = userRepository.findByUsername(user_.getUsername());
            if(optionalUser.isEmpty()){
                throw new BookNotFoundException(user_.getUsername(),"找不到用户");
            }
            System.out.println("=================识别的 user 是：：：：：：：：：：：：：：：：：：：：："+user_.toString());
            return optionalUser.get();
        }else{
            throw new AuthenticationServiceException("客户未登录认证");
        }
    }
}