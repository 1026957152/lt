package com.lt.dom.serviceOtc;

import com.lt.dom.oct.Openid;
import com.lt.dom.oct.User;
import com.lt.dom.oct.UserAuthority;
import com.lt.dom.oct.VerificationToken;
import com.lt.dom.otcenum.EnumSmsVerificationType;
import com.lt.dom.vo.UserVo;

import java.util.Optional;

public interface IUserService {
    
/*
    User registerNewUserAccount(UserDto userDto)
      throws UserAlreadyExistException;
*/

    User getUser(String verificationToken);

    void saveRegisteredUser(User user);

    void createVerificationToken(User user, String token);

    VerificationToken getVerificationToken(String VerificationToken);

    VerificationToken createVerificationToken(UserVo user, String token);

    VerificationToken createVerificationTokenForLogin(EnumSmsVerificationType login, String phone);

    Optional<VerificationToken> getByUuid(String id);

    VerificationToken verified_change(VerificationToken verificationToken1);

    VerificationToken retry(VerificationToken verificationToken);

















    VerificationToken verified_miniap(Openid openid, VerificationToken verificationToken1);


    UserAuthority verified_pc( VerificationToken verificationToken1);
}