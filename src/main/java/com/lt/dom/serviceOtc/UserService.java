package com.lt.dom.serviceOtc;

import com.lt.dom.oct.User;
import com.lt.dom.oct.VerificationToken;
import com.lt.dom.oct.VerificationTokenRepository;
import com.lt.dom.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerificationTokenRepository tokenRepository;

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
        User user = tokenRepository.findByToken(verificationToken).getUser();
        return user;
    }
    
    @Override
    public VerificationToken getVerificationToken(String VerificationToken) {
        return tokenRepository.findByToken(VerificationToken);
    }
    
    @Override
    public void saveRegisteredUser(User user) {
        userRepository.save(user);
    }
    
    @Override
    public void createVerificationToken(User user, String token) {
        VerificationToken myToken = new VerificationToken(token, user);
        tokenRepository.save(myToken);
    }
}