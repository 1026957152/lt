package com.lt.dom.config;

import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.Openid;
import com.lt.dom.oct.Privilege;
import com.lt.dom.oct.Role;
import com.lt.dom.oct.User;
import com.lt.dom.repository.OpenidRepository;
import com.lt.dom.repository.RoleRepository;
import com.lt.dom.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
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

    System.out.println("-----------------打印出loglgoggggggggggggggggggggggss打印出loglgoggggggggggggggggggggggss打印出loglgoggggggggggggggggggggggss打印出loglgoggggggggggggggggggggggss");
   // Assert.isTrue(!authentication.isAuthenticated(), "Already authenticated");
    AuthenticationToken authenticationToken = (AuthenticationToken) authentication;
    if (!StringUtils.hasText((String) authenticationToken.getToken())) {
     // throw new InternalAuthenticationServiceException("Token must not be empty");

    }

    Optional<Openid> optional = openidRepository.findByOpenid(authenticationToken.getToken());
    /**TODO do the logic here and return not null authentication object*/

    if(optional.isPresent()){

      if(!optional.get().getLink()){
        org.springframework.security.core.userdetails.User userDetails =  new org.springframework.security.core.userdetails.User(
                optional.get().getOpenid(), " ", true, true, true, true,
                getAuthorities(Arrays.asList(
                        roleRepository.findByName("ROLE_NOT_REAL_NAME"))));

        UsernamePasswordAuthenticationToken authentication_ = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        //authentication_.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        return authentication_;
      }else{
        Optional<User> user = userRepository.findById(optional.get().getUserId());
        if(user.isEmpty()){
          throw new BookNotFoundException("","无法找到绑定的 用户 user ");
        }
        System.out.println("--token认证认证认证goggggggggggggggggggggggss"+user.get().getUsername());

        org.springframework.security.core.userdetails.UserDetails userDetails =  userDetailsService.loadUserByUsername(user.get().getUsername());


        UsernamePasswordAuthenticationToken authentication_ = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        //authentication_.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        return authentication_;
      }

    }else{

      throw new AuthenticationServiceException("没有授权登录，请授权登录");
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