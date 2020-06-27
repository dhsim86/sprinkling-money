package com.kakaopay.sprinkling_money.presentation.web.controller.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakaopay.sprinkling_money.application.room.RoomInvitationAppService;
import com.kakaopay.sprinkling_money.presentation.web.controller.v1.protocol.InviteUsersRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.kakaopay.sprinkling_money.presentation.web.controller.v1.config.RequestHeaderConfig.USER_ID_HEADER_NAME;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RoomController.class)
public class RoomControllerTest {

    @MockBean
    private RoomInvitationAppService roomInvitationAppService;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void registerUserTest() throws Exception {
        String roomId;
        String requestBody;

        given:
        {
            roomId = UUID.randomUUID().toString();

            InviteUsersRequest request = new InviteUsersRequest();
            request.setName("test");
            request.setUserIdList(IntStream.range(0, 1).boxed().collect(Collectors.toList()));

            requestBody = objectMapper.writeValueAsString(request);

            doReturn(roomId).when(roomInvitationAppService).inviteRoom(anyInt(), anyString(), anyList());
        }

        then:
        {
            mockMvc.perform(post("/api/v1.0/rooms")
                    .header(USER_ID_HEADER_NAME, 1)
                    .content(requestBody)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isCreated())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(roomId));
        }
    }

    @Test
    public void registerUserMissingUserIdTest() throws Exception {
        String requestBody;

        given:
        {
            InviteUsersRequest request = new InviteUsersRequest();
            request.setName("test");
            request.setUserIdList(IntStream.range(0, 1).boxed().collect(Collectors.toList()));

            requestBody = objectMapper.writeValueAsString(request);
        }

        then:
        {
            mockMvc.perform(post("/api/v1.0/rooms")
                    .content(requestBody)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
        }
    }

    @Test
    public void registerUserBlankRoomNameTest() throws Exception {
        String requestBody;

        given:
        {
            InviteUsersRequest request = new InviteUsersRequest();
            request.setUserIdList(IntStream.range(0, 1).boxed().collect(Collectors.toList()));

            request.setName("");
            requestBody = objectMapper.writeValueAsString(request);
        }

        then:
        {
            mockMvc.perform(post("/api/v1.0/rooms")
                    .header(USER_ID_HEADER_NAME, 1)
                    .content(requestBody)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
        }
    }

    @Test
    public void registerUserLongRoomNameTest() throws Exception {
        String requestBody;

        given:
        {
            InviteUsersRequest request = new InviteUsersRequest();
            request.setUserIdList(IntStream.range(0, 1).boxed().collect(Collectors.toList()));

            String dummyName = IntStream.range(0, 65)
                    .mapToObj(i -> "x")
                    .collect(Collectors.joining());
            request.setName(dummyName);
            requestBody = objectMapper.writeValueAsString(request);
        }

        then:
        {
            mockMvc.perform(post("/api/v1.0/rooms")
                    .header(USER_ID_HEADER_NAME, 1)
                    .content(requestBody)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
        }
    }

    @Test
    public void registerUserNoUserIdListTest() throws Exception {
        String requestBody;

        given:
        {
            InviteUsersRequest request = new InviteUsersRequest();
            request.setName("test");

            requestBody = objectMapper.writeValueAsString(request);
        }

        then:
        {
            mockMvc.perform(post("/api/v1.0/rooms")
                    .header(USER_ID_HEADER_NAME, 1)
                    .content(requestBody)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
        }
    }

}
