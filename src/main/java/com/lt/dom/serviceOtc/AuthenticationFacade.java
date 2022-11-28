package com.lt.dom.serviceOtc;

import com.lt.dom.config.AuthenticationToken;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.error.Error401Exception;
import com.lt.dom.error.Error403Exception;
import com.lt.dom.error.ExistException;
import com.lt.dom.oct.User;
import com.lt.dom.otcenum.Enumfailures;
import com.lt.dom.repository.UserRepository;
import com.lt.dom.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;


@Component
public class AuthenticationFacade implements IAuthenticationFacade {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserVoServiceImpl userService;

    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    public User getUser(Authentication authentication) {

        if (authentication instanceof AnonymousAuthenticationToken) {

            System.out.println("=============需要实名认证啊啊");
            throw new Error403Exception(Enumfailures.Need_real_name,"需要实名认证");


        }else{
           // throw new ExistException("错误返回");
            if(authentication != null && authentication.isAuthenticated()){


                UserDetails user_ = (UserDetails)authentication.getPrincipal();
                System.out.println("=============不是匿名用户----- "+ user_.getUsername());
                Optional<User> optionalUser = userRepository.findByUsername(user_.getUsername());
                if(optionalUser.isEmpty()){

                    Optional<User> optional = userRepository.findByPhone(user_.getUsername());
                    if(optional.isEmpty()){
                        throw new BookNotFoundException(user_.getUsername(),"找不到用户");

                    }
                    return optional.get();
                }
                System.out.println("=================识别的 user 是：：：：：：：：：：：：：：：：：：：：："+user_.toString());
                return optionalUser.get();
            }else{
                throw new AuthenticationServiceException("客户未登录认证");
            }
        }


    }

    @Override
    public UserVo getUserVo(Authentication authentication) {

        if (authentication instanceof AnonymousAuthenticationToken) {

            System.out.println("=============需要实名认证啊啊");
       //     throw new Error403Exception(Enumfailures.Need_real_name, "需要实名认证");


        } else {
            // throw new ExistException("错误返回");
            if (authentication != null && authentication.isAuthenticated()) {

                UserDetails user_ = (UserDetails) authentication.getPrincipal();

 /*               if(authentication instanceof AuthenticationToken){
                    List<User> optionalUser = userRepository.findByOpenid(user_.getUsername());

                    if(optionalUser.isEmpty()){
                        throw new BookNotFoundException(Enumfailures.resource_not_found,"找不到用户");

                    }

                }else {*/

                List<User> optionalUser = userRepository.findByOpenid(user_.getUsername());

                if (!optionalUser.isEmpty()) {
                    // throw new BookNotFoundException(Enumfailures.resource_not_found,"找不到用户");
                    return userService.getInverUser(optionalUser.get(0));

                }
                System.out.println("=============不是匿名用户----- " + user_.getUsername());

                Optional<User> optional = userRepository.findByPhone(user_.getUsername());
                if (!optional.isEmpty()) {
                    return userService.getInverUser(optional.get());
                }


            }

            throw new AuthenticationServiceException("客户未登录认证");

        }


        return null;

    }
}