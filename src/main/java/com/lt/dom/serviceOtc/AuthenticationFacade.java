package com.lt.dom.serviceOtc;

import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.error.Error401Exception;
import com.lt.dom.error.Error403Exception;
import com.lt.dom.oct.User;
import com.lt.dom.otcenum.Enumfailures;
import com.lt.dom.repository.UserRepository;
import com.lt.dom.vo.CustomUserDetails;
import com.lt.dom.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

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
            throw new Error403Exception(Enumfailures.NEED_REAL_NAME,"需要实名认证");


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
    public UserVo checkUserVo(Authentication authentication) {

        System.out.println("=====查看登录情况啊啊啊");

        if (authentication instanceof AnonymousAuthenticationToken) {

            System.out.println("=============需要实名认证啊啊");
            //     throw new Error403Exception(Enumfailures.Need_real_name, "需要实名认证");


        } else {


            System.out.println("=====不是匿名对象");
            if (authentication != null && authentication.isAuthenticated()) {

                CustomUserDetails user_ = (CustomUserDetails) authentication.getPrincipal();

                Optional<User> optional = userRepository.findById(user_.getUserAuthority().getUser_id());
                if (!optional.isEmpty()) {
                    return userService.getInverUser(optional.get());
                }


            }


        }


        return null;

    }
    @Override
    public UserVo getUserVo(Authentication authentication) {

        if (authentication instanceof AnonymousAuthenticationToken) {

            System.out.println("=============需要实名认证啊啊");
       //     throw new Error403Exception(Enumfailures.Need_real_name, "需要实名认证");


        } else {
            // throw new ExistException("错误返回");
            if (authentication != null && authentication.isAuthenticated()) {

                CustomUserDetails user_ = (CustomUserDetails) authentication.getPrincipal();

 /*               if(authentication instanceof AuthenticationToken){
                    List<User> optionalUser = userRepository.findByOpenid(user_.getUsername());

                    if(optionalUser.isEmpty()){
                        throw new BookNotFoundException(Enumfailures.resource_not_found,"找不到用户");

                    }

                }else {*/

//                user_.getUserAuthority().getUser_id();
//
//                List<User> optionalUser = userRepository.findByOpenid(user_.getUsername());
//
//                if (!optionalUser.isEmpty()) {
//                    // throw new BookNotFoundException(Enumfailures.resource_not_found,"找不到用户");
//                    return userService.getInverUser(optionalUser.get(0));
//
//                }
//                System.out.println("=============不是匿名用户----- " + user_.getUsername());

                Optional<User> optional = userRepository.findById(user_.getUserAuthority().getUser_id());
                if (!optional.isEmpty()) {
                    return userService.getInverUser(optional.get());
                }


            }

            throw new AuthenticationServiceException("客户未登录认证");

        }


        return null;

    }


    @Override
    public UserVo getUserVoWithUser(Authentication authentication) {

        if (authentication instanceof AnonymousAuthenticationToken) {

            System.out.println("=============需要实名认证啊啊");

            throw new Error401Exception(Enumfailures._401_Unauthorized,"需要注册或登录");


        } else {

            if (authentication != null && authentication.isAuthenticated()) {

                CustomUserDetails user_ = (CustomUserDetails) authentication.getPrincipal();

                Optional<User> optional = userRepository.findById(user_.getUserAuthority().getUser_id());


                if (!optional.isEmpty()) {
                    User user = optional.get();



                    return userService.getInverUser(optional.get());
                }


            }

            throw new AuthenticationServiceException("客户未登录认证");

        }


    }

    @Override
    public UserVo getUserVoWithUserRealname(Authentication authentication) {

        if (authentication instanceof AnonymousAuthenticationToken) {

            System.out.println("=============需要实名认证啊啊");

            throw new Error401Exception(Enumfailures._401_Unauthorized,"需要注册或登录");


        } else {

            if (authentication != null && authentication.isAuthenticated()) {

                CustomUserDetails user_ = (CustomUserDetails) authentication.getPrincipal();

                Optional<User> optional = userRepository.findById(user_.getUserAuthority().getUser_id());


                if (!optional.isEmpty()) {
                    User user = optional.get();

                    if(!user.isRealNameVerified()){

                        throw new Error403Exception(Enumfailures.NEED_REAL_NAME,"需要实名");

                    }


                    return userService.getInverUser(optional.get());
                }


            }

            throw new AuthenticationServiceException("客户未登录认证");

        }


    }
}