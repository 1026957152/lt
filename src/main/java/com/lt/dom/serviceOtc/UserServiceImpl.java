package com.lt.dom.serviceOtc;


import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.error.ExistException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.UserPojo;
import com.lt.dom.otcenum.*;
import com.lt.dom.repository.*;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

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



    public User userAuth(User finalUser, List<Pair<EnumIdentityType,String>> enumIdentityTypes) {


        enumIdentityTypes = enumIdentityTypes.stream().filter(e->{
            Optional<UserAuthority> userAuthority = userAuthorityRepository.findByIdentityTypeAndIdentifier(e.getValue0(),e.getValue1());

            return userAuthority.isEmpty();

        }).collect(Collectors.toList());

        enumIdentityTypes.forEach(x->{
            UserAuthority userAuthority = new UserAuthority();
            userAuthority.setUserId(finalUser.getId());


            EnumIdentityType type = x.getValue0();
            String identifier = x.getValue1();
            userAuthority.setIdentityType(type);
            switch (type){
                case phone:
                    userAuthority.setIdentifier(identifier);
                    userAuthority.setCredential(passwordEncoder.encode("123"));

                    userAuthorityRepository.save(userAuthority);
                    break;
                case weixin:
                    userAuthority.setIdentifier(identifier);
                    userAuthority.setCredential(passwordEncoder.encode("123"));

                    userAuthorityRepository.save(userAuthority);
                    break;
                case identity_card:
                    userAuthority.setIdentifier(identifier);
                    userAuthority.setCredential(passwordEncoder.encode("123"));

                    userAuthorityRepository.save(userAuthority);
                    break;
            }



        });

        return finalUser;
    }



    public User createUser(@Valid UserPojo pojo, List<Pair<EnumIdentityType,String>> enumIdentityTypes) {



        EnumIdentityType enumIdentityType = enumIdentityTypes.get(0).getValue0();
        String identifier_ = enumIdentityTypes.get(0).getValue1();
        Optional<UserAuthority> optionalUserAuthority = userAuthorityRepository.findByIdentityTypeAndIdentifier(enumIdentityType,identifier_);
        if(optionalUserAuthority.isPresent()){
            throw new ExistException(Enumfailures.username_already_exists_error, enumIdentityType+" 已经注册" + identifier_);

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


        user = userAuth(user,enumIdentityTypes);


        assetService.getWithNew(user);

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

    public User createRoleIfNotFound(User user, List<String> name) {

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


    public void verifyPhone(VerificationToken verificationToken____) {
        Optional<User> optionalUser = userRepository.findByCode(verificationToken____.getUserCode());
        if(optionalUser.isPresent()){
            Optional<User> userList = userRepository.findByPhone(verificationToken____.getPhone());

            if(userList.isPresent()){
                User user_older = userList.get();
                user_older.setPhone(null);
                user_older.setPhoneVerifid(false);
                userRepository.save(user_older);
            }

            User user = optionalUser.get();
            user.setPhoneVerifid(true);
            user.setPhone(verificationToken____.getPhone());
            userRepository.save(user);

            Optional<UserAuthority> optionalUserAuthority = userAuthorityRepository.findByIdentityTypeAndIdentifier(EnumIdentityType.phone,verificationToken____.getPhone());

            if(optionalUserAuthority.isPresent()){
                UserAuthority userAuthority = optionalUserAuthority.get();
                userAuthority.setUserId(user.getId());
                userAuthorityRepository.save(userAuthority);
            }else{
                UserAuthority userAuthority = new UserAuthority();
                userAuthority.setIdentifier(verificationToken____.getPhone());
                userAuthority.setIdentityType(EnumIdentityType.phone);

                userAuthority.setCredential(passwordEncoder.encode("123"));
                userAuthority.setUserId(user.getId());
                userAuthorityRepository.save(userAuthority);
            }
        }

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
