package com.kakaopay.sprinkling_money.domain.user;

import com.kakaopay.sprinkling_money.domain.user.exception.UserException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class UserServiceTest {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    public void registerUserTest() {
        String userName = "test";
        int userId;

        int resultUserId;

        given:
        {
            userId = 0;

            User user = new User(userName);
            user.setId(userId);

            doReturn(user).when(userRepository).save(any(User.class));
        }

        when:
        {
            resultUserId = userService.registerUser(userName);
        }

        then:
        {
            assertThat(resultUserId).isEqualTo(userId);
        }
    }

    @Test
    public void registerUserFailureTest() {
        String userName = "test";

        given:
        {
            doThrow(mock(DataAccessException.class)).when(userRepository).save(any(User.class));
        }

        then:
        {
            assertThrows(UserException.class, () -> userService.registerUser(userName));
        }
    }

}
