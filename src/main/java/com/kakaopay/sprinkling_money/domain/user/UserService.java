package com.kakaopay.sprinkling_money.domain.user;

import com.kakaopay.sprinkling_money.domain.user.exception.UserException;
import com.kakaopay.sprinkling_money.domain.user.exception.code.UserExceptionCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public int registerUser(String userName) {
        try {
            User user = new User(userName);
            userRepository.save(user);
            return user.getId();
        } catch (Exception ex) {
            throw new UserException(ex, UserExceptionCode.USER_REGISTER_FAILED, userName);
        }
    }

}
