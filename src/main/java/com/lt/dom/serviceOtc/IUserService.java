package com.lt.dom.serviceOtc;

import com.lt.dom.oct.User;
import com.lt.dom.oct.VerificationToken;

public interface IUserService {
    
/*
    User registerNewUserAccount(UserDto userDto)
      throws UserAlreadyExistException;
*/

    User getUser(String verificationToken);

    void saveRegisteredUser(User user);

    void createVerificationToken(User user, String token);

    VerificationToken getVerificationToken(String VerificationToken);
}