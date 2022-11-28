package com.lt.dom.serviceOtc;

import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.Openid;
import com.lt.dom.oct.User;
import com.lt.dom.oct.UserAuthority;
import com.lt.dom.oct.VerificationToken;
import com.lt.dom.otcReq.UserPojo;
import com.lt.dom.otcenum.EnumIdentityType;
import com.lt.dom.otcenum.EnumSmsVerificationType;
import com.lt.dom.otcenum.EnumVefifyStatus;
import com.lt.dom.otcenum.Enumfailures;
import com.lt.dom.repository.UserAuthorityRepository;
import com.lt.dom.repository.VerificationTokenRepository;
import com.lt.dom.repository.UserRepository;
import com.lt.dom.vo.UserVo;
import org.apache.commons.lang.RandomStringUtils;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SMSServiceImpl smsService;


    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private OpenidServiceImpl openidService;



    @Autowired
    private VerificationTokenRepository tokenRepository;

    @Autowired
    private UserAuthorityRepository userAuthorityRepository;



/*    @Override
    public User registerNewUserAccount(UserDto userDto)
      throws UserAlreadyExistException {
        
        if (emailExist(userDto.getEmail())) {
            throw new UserAlreadyExistException(
              "There is an account with that email adress: " 
              + userDto.getEmail());
        }
        
        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());
     //   user.setRole(new Role(Integer.valueOf(1), user));
        return userRepository.save(user);
    }*/

    private boolean emailExist(String email) {
        return userRepository.findByEmail(email) != null;
    }
    
    @Override
    public User getUser(String verificationToken) {
        User user = tokenRepository.findByToken(verificationToken).get().getUser();
        return user;
    }
    
    @Override
    public VerificationToken getVerificationToken(String VerificationToken) {
        return tokenRepository.findByToken(VerificationToken).get();
    }
    
    @Override
    public void saveRegisteredUser(User user) {
        userRepository.save(user);
    }
    
    @Override
    public void createVerificationToken(User user, String token) {
        VerificationToken myToken = new VerificationToken(token, user);
        myToken.setStatus(EnumVefifyStatus.pending);
        tokenRepository.save(myToken);
    }


    @Override
    public VerificationToken createVerificationToken(UserVo user, String phone) {

        User user1 = userRepository.findById(user.getUser_id()).get();


        List<VerificationToken> token1 = tokenRepository.findByPhoneAndUserCode(phone,user.getUser_code());

        if(!token1.isEmpty()) {

            Optional<VerificationToken> verificationTokenOptional =  token1.stream().filter(e -> e.getStatus().equals(EnumVefifyStatus.pending)).findAny();

            if(verificationTokenOptional.isPresent()){
                VerificationToken verificationToken = verificationTokenOptional.get();

              //  if(verificationToken.getExpiryDate().before(LocalDateTime.now()))

                return verificationTokenOptional.get();
            }
        }
            VerificationToken myToken = new VerificationToken();

            String id = UUID.randomUUID().toString();
            myToken.setUuid(id);
            myToken.setUser(user1);
            myToken.setUserCode(user.getUser_code());
        String generatedString = RandomStringUtils.randomNumeric(4);

        myToken.setToken(generatedString);
            myToken.setPhone(phone);
            myToken.setStatus(EnumVefifyStatus.pending);

            myToken.setExpiryDate(LocalDateTime.now().plusMinutes(1));
            return tokenRepository.save(myToken);

    }

    @Override
    public VerificationToken createVerificationTokenForLogin(EnumSmsVerificationType login, String phone) {




        List<VerificationToken> token1 = tokenRepository.findByPhoneAndType(phone, login);

        if(!token1.isEmpty()) {

            Optional<VerificationToken> verificationTokenOptional =  token1.stream().filter(e -> e.getStatus().equals(EnumVefifyStatus.pending)).findAny();

            if(verificationTokenOptional.isPresent()){
                VerificationToken verificationToken = verificationTokenOptional.get();

                //  if(verificationToken.getExpiryDate().before(LocalDateTime.now()))

                if(verificationToken.getExpiryDate().isAfter(LocalDateTime.now())) {
                    throw new BookNotFoundException(Enumfailures.resource_not_found,"短信发送太频繁，稍等发送");

                }


                verificationToken = retry(verificationToken);
                String greetings = String.format(
                        "你的验证码是%s",
                        verificationToken.getToken());

                System.out.println("-------发了短信，发了短信");
                try{
                    smsService.singleSend(greetings,verificationToken.getPhone());

                }catch (Exception e){
                    e.printStackTrace();
                }
                return verificationToken;
            }
        }
        VerificationToken myToken = new VerificationToken();

        String id = UUID.randomUUID().toString();
        myToken.setUuid(id);
        myToken.setType(login);
      //  myToken.setUserCode(user.getUser_code());
        String generatedString = RandomStringUtils.randomNumeric(4);

        myToken.setToken(generatedString);
        myToken.setPhone(phone);
        myToken.setStatus(EnumVefifyStatus.pending);

        myToken.setExpiryDate(LocalDateTime.now().plusMinutes(1));
        myToken =  tokenRepository.save(myToken);



        String greetings = String.format(
                "你的验证码是%s",
                myToken.getToken());

        System.out.println("-------发了短信，发了短信"+myToken.getToken());
        smsService.singleSend(greetings,myToken.getPhone());

        return myToken;
    }
    @Override
    public Optional<VerificationToken> getByUuid(String id) {


        Optional<VerificationToken> verificationTokenOptional = tokenRepository.findByUuid(id);

        return verificationTokenOptional;
    }

    @Override
    @Transactional
    public VerificationToken verified(VerificationToken verificationToken1) {



        verificationToken1.setStatus(EnumVefifyStatus.approved);
        verificationToken1 = tokenRepository.save(verificationToken1);
        if(verificationToken1.getType().equals(EnumSmsVerificationType.login_pc)){

            Optional<UserAuthority> userAuthority = userAuthorityRepository.findByIdentityTypeAndIdentifier(EnumIdentityType.phone,verificationToken1.getPhone());



        }else{
            userService.verifyPhone(verificationToken1);
        }


        return verificationToken1;
    }


    @Override
    public VerificationToken retry(VerificationToken verificationToken) {

        verificationToken.setExpiryDate(LocalDateTime.now().plusMinutes(1));
        return tokenRepository.save(verificationToken);

    }

    @Override
    @Transactional
    public VerificationToken verified(Openid openid, VerificationToken verificationToken1) {


        verificationToken1.setStatus(EnumVefifyStatus.approved);
        verificationToken1 = tokenRepository.save(verificationToken1);
        if(verificationToken1.getType().equals(EnumSmsVerificationType.login_miniapp)){

            Optional<UserAuthority> userAuthority = userAuthorityRepository.findByIdentityTypeAndIdentifier(EnumIdentityType.phone,verificationToken1.getPhone());

            if(userAuthority.isEmpty()){


                UserPojo userPojo = new UserPojo();
                //userPojo.setFirst_name(wxlinkUserReq.getFirst_name());
                //userPojo.setLast_name(wxlinkUserReq.getLast_name());
                userPojo.setUsername(verificationToken1.getPhone());

                userPojo.setPhone(verificationToken1.getPhone());
                userPojo.setPassword("wxlinkUserReq.getUser_password()");
                userPojo.setRoles(Arrays.asList("ROLE_ADMIN"));

                User user = userService.createUser(userPojo,Arrays.asList(Pair.with(EnumIdentityType.phone,verificationToken1.getPhone())));
                user = userService.userAuth(user,Arrays.asList(Pair.with(EnumIdentityType.weixin,openid.getOpenid())));

                openidService.linkUser(openid,user);
            }else{
                Optional<User> optionalUser = userRepository.findById(userAuthority.get().getUser_id());
                User user = userService.userAuth(optionalUser.get(),Arrays.asList(Pair.with(EnumIdentityType.weixin,openid.getOpenid())));


                openidService.linkUser(openid,optionalUser.get());
            }


        }else{
            userService.verifyPhone(verificationToken1);
        }


        return verificationToken1;
    }



    @Override
    @Transactional
    public UserAuthority verified_pc(VerificationToken verificationToken1) {


        verificationToken1.setStatus(EnumVefifyStatus.approved);
        verificationToken1 = tokenRepository.save(verificationToken1);
        if(verificationToken1.getType().equals(EnumSmsVerificationType.login_pc)){

            Optional<UserAuthority> userAuthority = userAuthorityRepository.findByIdentityTypeAndIdentifier(EnumIdentityType.phone,verificationToken1.getPhone());

            if(!userAuthority.isEmpty()){

                return userAuthority.get();
            }

        }

        throw new BookNotFoundException(Enumfailures.resource_not_found,"登录失败");



    }
}