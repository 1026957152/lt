package com.lt.dom.serviceOtc;


import com.lt.dom.OctResp.*;
import com.lt.dom.controllerOct.*;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.error.ExistException;
import com.lt.dom.error.username_already_exists_errorException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.UserPojo;
import com.lt.dom.otcenum.*;
import com.lt.dom.repository.*;
import com.lt.dom.vo.GuideSummaryVo;
import com.lt.dom.vo.UserVo;
import org.javatuples.Pair;
import org.springdoc.webmvc.core.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class UserServiceImpl {




    @Autowired
    private RequestRepository requestRepository;
    @Autowired
    private OpenidRepository openidRepository;

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private GuideRepository guideRepository;



    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private IdGenServiceImpl idGenService;
    @Autowired
    UserAuthorityRepository userAuthorityRepository;
    @Autowired
    GuideServiceImpl guideService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AssetServiceImpl assetService;


    public User createUser(@Valid UserPojo pojo,List<Pair<EnumIdentityType,String>> enumIdentityTypes) {

        Optional<User> optional = userRepository.findByPhone(pojo.getPhone());
        if(optional.isPresent()){
            throw new ExistException(Enumfailures.username_already_exists_error, pojo.getPhone()+"手机号已经注册");
        }
        User user = new User();


        user.setFirst_name(pojo.getFirst_name());
        user.setLast_name(pojo.getLast_name());
        user.setRealName(pojo.getRealName());
        user.setIdCard(pojo.getIdCard());
        user.setIdCardType(1);
        user.setUsername(pojo.getUsername());
        user.setNick_name(pojo.getNick_name());
        user.setPhone(pojo.getPhone());
        user.setPassword(passwordEncoder.encode(pojo.getPassword()));
        user.setCode(idGenService.userNo());

       // user.setCreated_at(LocalDateTime.now());

        createRoleIfNotFound(user,pojo.getRoles());

        //createRoleIfNotFound(user,"ROLE_ADMIN");
        user.setEnabled(true);
        user = userRepository.save(user);


        User finalUser = user;
        enumIdentityTypes.forEach(x->{
            UserAuthority userAuthority = new UserAuthority();
            userAuthority.setUser_id(finalUser.getId());

            EnumIdentityType type = x.getValue0();
            String identifier = x.getValue1();
            switch (type){
                case phone:
                    userAuthority.setIdentifier(identifier);
                    userAuthorityRepository.save(userAuthority);
                    break;
                case weixin:
                    userAuthority.setIdentifier(identifier);
                    userAuthorityRepository.save(userAuthority);
                    break;
                case identity_card:
                    userAuthority.setIdentifier(identifier);
                    userAuthorityRepository.save(userAuthority);
                    break;
            }



        });



        assetService.newQr(user);

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




    public Optional<User> getActiveOne(String real_name, String id_card, String phone) {
        Optional<User> optionalUser = userRepository.findByRealNameAndIdCardAndEnabled(real_name,id_card,true);


        return optionalUser;

    }

    public Optional<User> getActiveOneByPhone(String s) {
        Optional<User> optionalUser = userRepository.findByPhoneAndEnabled(s,true);
        return optionalUser;
    }



}



/*
image.*.id	string
        The asset ID

        image.*.url	string
        The asset URL that you may use to serve the asset

        image.*.description	string
        A description of the asset

        image.*.is_image	boolean
        Whether the asset is an image

        image.*.filename	string
        The original filename that the file was uploaded with

        image.*.file_extension	string
        The file extension for the asset

        image.*.file_size	integer
        The file size in bytes

        image.*.image_dimensions.width	integer
        The width in pixels (if the asset is an image)

        image.*.image_dimensions.height	integer
        The height in pixels (if the asset is an image)

        image.*.meta	object
        A given array or keyed object with metadata

        image.*.created_at	integer
        A unix timestamp when the asset was originally uploaded*/
