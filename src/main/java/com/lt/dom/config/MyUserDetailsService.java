package com.lt.dom.config;

import com.lt.dom.oct.Privilege;
import com.lt.dom.oct.Role;
import com.lt.dom.oct.User;
import com.lt.dom.repository.RoleRepository;
import com.lt.dom.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service("userDetailsService")

public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
 
   // @Autowired
   // private IUserService service;
 
    @Autowired
    private MessageSource messages;
 
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String email)
      throws UsernameNotFoundException {
        System.out.println("空的吗--"+email);
        List<User> list = userRepository.findAll();
        System.out.println("空的吗 多少-"+list.size());
        list.stream().forEach(x->{
            System.out.println("kankan"+x.getPhone());
        });
        for(User u : list){
            System.out.println("2222"+u.getPhone());

        }
        Optional<User> optionalUser = userRepository.findByUsername(email);
        if (optionalUser.isEmpty()) {
            System.out.println("空的吗"+email);
            return new org.springframework.security.core.userdetails.User(
              " ", " ", true, true, true, true, 
              getAuthorities(Arrays.asList(
                roleRepository.findByName("ROLE_USER"))));
        }
        User user = optionalUser.get();
        user.setEnabled(true);
        try {
            System.out.println("ddddddddddddd"+user.getRoles().size());
            System.out.println("ddddddddddddd"+user.toString());

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("ddddddddddddd"+user.toString());

        }

        return new org.springframework.security.core.userdetails.User(
          user.getUsername(), user.getPassword(), user.isEnabled(), true, true,
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