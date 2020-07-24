package com.kakaopay.sprinkling_money.presentation.web.controller.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakaopay.sprinkling_money.domain.user.UserService;
import com.kakaopay.sprinkling_money.presentation.web.controller.v1.protocol.RegisterUserRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void registerUserTest() throws Exception {
        int resultUserId;
        String requestBody;

        given:
        {
            resultUserId = 1;
            doReturn(resultUserId).when(userService).registerUser(anyString());

            RegisterUserRequest request = new RegisterUserRequest();
            request.setName("test");

            requestBody = objectMapper.writeValueAsString(request);
        }

        then:
        {
            mockMvc.perform(post("/api/v1.0/users")
                    .content(requestBody)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isCreated())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));
        }
    }

    @Test
    public void registerUserNameBlankTest() throws Exception {
        String requestBody;

        given:
        {
            RegisterUserRequest request = new RegisterUserRequest();
            request.setName("");

            requestBody = objectMapper.writeValueAsString(request);
        }

        then:
        {
            mockMvc.perform(post("/api/v1.0/users")
                    .content(requestBody)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
        }
    }

    @Test
    public void registerUserLongNameTest() throws Exception {
        String requestBody;

        given:
        {
            RegisterUserRequest request = new RegisterUserRequest();
            String dummyName = IntStream.range(0, 65)
                    .mapToObj(i -> "x")
                    .collect(Collectors.joining());
            request.setName(dummyName);

            requestBody = objectMapper.writeValueAsString(request);
        }

        then:
        {
            mockMvc.perform(post("/api/v1.0/users")
                    .content(requestBody)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
        }
    }

}
