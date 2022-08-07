package com.lt.dom.serviceOtc;


import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.error.username_already_exists_errorException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.UserPojo;
import com.lt.dom.repository.BookingRuleRepository;
import com.lt.dom.repository.RoleRepository;
import com.lt.dom.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl {
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private IdGenServiceImpl idGenService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User createUser(@Valid UserPojo pojo) {

        Optional<User> optional = userRepository.findByPhone(pojo.getPhone());
        if(optional.isPresent()){
            throw new username_already_exists_errorException(1,pojo.getPhone()+"手机号已经注册","手机号已经注册");
        }
        User user = new User();


        user.setFirst_name(pojo.getFirst_name());
        user.setLast_name(pojo.getLast_name());
        user.setRealName(pojo.getRealName());
        user.setUsername(pojo.getUsername());
        user.setPhone(pojo.getPhone());
        user.setPassword(passwordEncoder.encode(pojo.getPassword()));
        user.setCode(idGenService.userNo());


        createRoleIfNotFound(user,pojo.getRoles());
        //createRoleIfNotFound(user,"ROLE_ADMIN");
        user = userRepository.save(user);
        return user;
    }

    //@Transactional
    User createRoleIfNotFound(User user,String name) {

        Role role = roleRepository.findByName(name);
        if (role != null) {
            user.setRoles(new HashSet<>(Arrays.asList(role)));
            roleRepository.save(role);
        }else{

        }
        return user;
    }

    User createRoleIfNotFound(User user,List<String> name) {

        List<Role> role = roleRepository.findByNameIn(name);
        if (role.size()> 0 && role.size() == name.size()) {
            user.setRoles(new HashSet<>(role));
           // roleRepository.saveAll(role);
        }else{
            throw new BookNotFoundException(0,"找不到权限"+name);
        }
        return user;
    }
}
