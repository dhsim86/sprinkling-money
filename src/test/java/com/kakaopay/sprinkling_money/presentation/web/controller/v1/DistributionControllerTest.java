package com.kakaopay.sprinkling_money.presentation.web.controller.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakaopay.sprinkling_money.application.distribution.ExecuteDistributionAppService;
import com.kakaopay.sprinkling_money.application.distribution.InquiryDistributionAppService;
import com.kakaopay.sprinkling_money.application.distribution.ReceiveDistributionAppService;
import com.kakaopay.sprinkling_money.common.secure.SecureRandomGenerator;
import com.kakaopay.sprinkling_money.domain.Money;
import com.kakaopay.sprinkling_money.presentation.web.controller.v1.protocol.ReceiveDistributionRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

import static com.kakaopay.sprinkling_money.presentation.web.controller.v1.config.RequestHeaderConfig.ROOM_ID_HEADER_NAME;
import static com.kakaopay.sprinkling_money.presentation.web.controller.v1.config.RequestHeaderConfig.USER_ID_HEADER_NAME;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DistributionController.class)
public class DistributionControllerTest {

    @MockBean
    private ExecuteDistributionAppService executeDistributionAppService;

    @MockBean
    private ReceiveDistributionAppService receiveDistributionAppService;

    @MockBean
    private InquiryDistributionAppService inquiryDistributionAppService;

    @Autowired
    private MockMvc mockMvc;

    private SecureRandomGenerator secureRandomGenerator = new SecureRandomGenerator();

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void executeDistributionMissingUserIdHeaderTest() throws Exception {
        then:
        {
            mockMvc.perform(post("/api/v1.0/distributions/execute")
                    .header(ROOM_ID_HEADER_NAME, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
        }
    }


    @Test
    public void receiveDistributionTest() throws Exception {
        String requestBody;
        String tokenValue;

        given:
        {
            ReceiveDistributionRequest request = new ReceiveDistributionRequest();
            tokenValue = secureRandomGenerator.generateRandomString(3);
            request.setToken(tokenValue);

            requestBody = objectMapper.writeValueAsString(request);

            doReturn(new Money(1000L)).when(receiveDistributionAppService).receiveDistribution(anyInt(), anyString(), anyString());
        }
        then:
        {
            mockMvc.perform(post("/api/v1.0/distributions/receive")
                    .header(USER_ID_HEADER_NAME, 1)
                    .header(ROOM_ID_HEADER_NAME, UUID.randomUUID().toString())
                    .content(requestBody)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.moneyAmount").value(1000L));
        }
    }

}
