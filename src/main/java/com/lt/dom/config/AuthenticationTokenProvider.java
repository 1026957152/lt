package com.lt.dom.config;

import com.google.gson.Gson;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.error.Error401Exception;
import com.lt.dom.error.UserNotFoundException;
import com.lt.dom.oct.Openid;
import com.lt.dom.oct.Privilege;
import com.lt.dom.oct.Role;
import com.lt.dom.oct.User;
import com.lt.dom.otcenum.EnumIdentityType;
import com.lt.dom.otcenum.Enumfailures;
import com.lt.dom.repository.OpenidRepository;
import com.lt.dom.repository.RoleRepository;
import com.lt.dom.repository.UserRepository;
import com.lt.dom.vo.IdentityVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.*;

@Component
public class AuthenticationTokenProvider implements AuthenticationProvider {


  @Autowired
  private RoleRepository roleRepository;
  @Autowired
  private OpenidRepository openidRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private MyUserDetailsService userDetailsService;



  public Authentication authenticate(Authentication authentication) throws AuthenticationException {

    System.out.println("--进入了验证， 看看什么 类型"+ authentication.getName());
   // Assert.isTrue(!authentication.isAuthenticated(), "Already authenticated");
    AuthenticationToken authenticationToken = (AuthenticationToken) authentication;
    if (!StringUtils.hasText((String) authenticationToken.getToken())) {
      //throw new InternalAuthenticationServiceException("Token must not be empty");
      throw new Error401Exception(Enumfailures.Missing_credentials,"Token 不能为空 ");
    }

    Gson gson = new Gson();
    IdentityVo identityVo = gson.fromJson(authenticationToken.getToken(),IdentityVo.class);


    Optional<Openid> optional = openidRepository.findByOpenid(identityVo.getCredential());
    /**TODO do the logic here and return not null authentication object*/

    if(optional.isEmpty()){

        throw new Error401Exception(Enumfailures.Invalid_credentials,"系统没有 openid 登录授权，请先微信登录");

      }
      Openid openid = optional.get();
      if(!openid.getLink()){


        System.out.println("返回匿名 AnonymousAuthenticationToken");
        return new AnonymousAuthenticationToken(optional.get().getOpenid(), optional.get().getOpenid(), AuthorityUtils.createAuthorityList("ANON"));

   //     throw new Error401Exception(Enumfailures.Invalid_credentials,"无法找到绑定的 用户 user ");



/*        org.springframework.security.core.userdetails.User userDetails =  new org.springframework.security.core.userdetails.User(
                optional.get().getOpenid(), " ", true, true, true, true,
                getAuthorities(Arrays.asList(
                        roleRepository.findByName("ROLE_NOT_REAL_NAME"))));

        UsernamePasswordAuthenticationToken authentication_ = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());

        //authentication_.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        return authentication_;*/
      }else{
        Optional<User> user = userRepository.findById(openid.getUserId());
        if(user.isEmpty()){
          throw new Error401Exception(Enumfailures.Missing_credentials,"无法找到绑定的 用户 user ");

        }
        System.out.println("--token认证认证认证goggggggggggggggggggggggss"+user.get().getUsername());


        //TODO 这里 给 赋值 getUsername 是否合适呀，  卫视不复制 getPhone 等


        IdentityVo identityVo_ = new IdentityVo(EnumIdentityType.weixin,user.get().getUsername());

        org.springframework.security.core.userdetails.UserDetails userDetails =  userDetailsService.loadUserByUsername(gson.toJson(identityVo_));


        UsernamePasswordAuthenticationToken authentication_ = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        //authentication_.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        return authentication_;
      }



  }

  public boolean supports(Class<?> authentication) {
    return (AuthenticationToken.class.isAssignableFrom(authentication));
  }



  private Collection<? extends GrantedAuthority> getAuthorities(
          Collection<Role> roles) {

    List<GrantedAuthority> grantedAuthorities= getGrantedAuthorities(getPrivileges(roles));

    System.out.println(grantedAuthorities+"dddddddddddddddd");
    return grantedAuthorities;
  }

  private List<String> getPrivileges(Collection<Role> roles) {

    List<String> privileges = new ArrayList<>();
    List<Privilege> collection = new ArrayList<>();
    for (Role role : roles) {
      privileges.add(role.getName());
      collection.addAll(role.getPrivileges());
    }
    for (Privilege item : collection) {
      privileges.add(item.getName());
    }
    return privileges;
  }

  private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
    List<GrantedAuthority> authorities = new ArrayList<>();
    for (String privilege : privileges) {
      authorities.add(new SimpleGrantedAuthority(privilege));
    }
    return authorities;
  }
}