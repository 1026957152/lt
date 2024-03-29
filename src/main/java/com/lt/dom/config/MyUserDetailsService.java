package com.lt.dom.config;

import com.google.gson.Gson;
import com.lt.dom.error.Error401Exception;
import com.lt.dom.oct.Privilege;
import com.lt.dom.oct.Role;
import com.lt.dom.oct.User;
import com.lt.dom.oct.UserAuthority;
import com.lt.dom.otcenum.EnumIdentityType;
import com.lt.dom.otcenum.Enumfailures;
import com.lt.dom.repository.RoleRepository;
import com.lt.dom.repository.UserAuthorityRepository;
import com.lt.dom.repository.UserRepository;
import com.lt.dom.vo.CustomUserDetails;
import com.lt.dom.vo.IdentityVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("userDetailsService")

public class MyUserDetailsService implements UserDetailsService {
    Logger logger = LoggerFactory.getLogger(MyUserDetailsService.class);

    @Autowired
    private UserRepository userRepository;
 
   // @Autowired
   // private IUserService service;
 
    @Autowired
    private MessageSource messages;
 
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserAuthorityRepository userAuthorityRepository;

    @Override
    public UserDetails loadUserByUsername(String ident)
      throws UsernameNotFoundException {

        Gson gson = new Gson();

        IdentityVo identityVo = gson.fromJson(ident,IdentityVo.class);

        logger.info("登录 {}, {}",identityVo.getType(),identityVo.getCredential());
/*
        String email = identityVo.getCredential();

      logger.debug("登录 username:{}",email);
        System.out.println("空的吗--"+email);
        List<User> list = userRepository.findAll();
        System.out.println("空的吗 多少-"+list.size());
        list.stream().forEach(x->{
            System.out.println("kankan"+x.getPhone());
        });
        for(User u : list){
            System.out.println("2222"+u.getPhone());

        }*/
        Optional<User> optionalUser = Optional.empty();
        String username = null;
        UserAuthority userAuthority = null;

        switch (identityVo.getType()){
            case phone->{
                Optional<UserAuthority> optionalUserAuthority = userAuthorityRepository.findByIdentityTypeAndIdentifier(EnumIdentityType.phone,identityVo.getCredential());
                if(optionalUserAuthority.isPresent()){
                    userAuthority = optionalUserAuthority.get();
                    optionalUser = userRepository.findById(userAuthority.getUserId());

                    username = optionalUser.get().getPhone();
                }

            }
            case weixin -> {
                Optional<UserAuthority> optionalUserAuthority = userAuthorityRepository.findByIdentityTypeAndIdentifier(EnumIdentityType.weixin,identityVo.getCredential());
                if(optionalUserAuthority.isPresent()){
                    userAuthority = optionalUserAuthority.get();
                    optionalUser = userRepository.findById(userAuthority.getUserId());
                    username = userAuthority.getIdentifier();
                }
            }
            case user_code -> {
                Optional<UserAuthority> optionalUserAuthority = userAuthorityRepository.findByIdentityTypeAndIdentifier(EnumIdentityType.user_code,identityVo.getCredential());
                if(optionalUserAuthority.isPresent()){
                    userAuthority = optionalUserAuthority.get();
                    optionalUser = userRepository.findById(userAuthority.getUserId());
                    username = optionalUser.get().getCode();
                }
            }
        }


        if(optionalUser.isEmpty()){
            String s = "微信，和 手机 号 找不到用户"+ identityVo.getType()+" "+identityVo.getCredential();
            System.out.println(s);
            throw new Error401Exception(Enumfailures.Missing_credentials,"找不到用户",s);

        }


        User user = optionalUser.get();
        user.setEnabled(true);
 /*       try {
            System.out.println("ddddddddddddd"+user.getRoles().size());
            System.out.println("ddddddddddddd"+user.toString());

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("ddddddddddddd"+user.toString());

        }*/


        System.out.println("找到了而设备ia啊啊啊啊啊"+username+ "------"+user.getPassword()); // user.isEnabled()
        return new CustomUserDetails(userAuthority,userAuthority.getIdentifier(), userAuthority.getCredential(), user.getEnabled(), true, true,
          true, getAuthorities(user.getRoles()));
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