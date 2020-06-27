package com.kakaopay.sprinkling_money.domain.distribution;

import com.kakaopay.sprinkling_money.common.distribution.DistributionConfiguration;
import com.kakaopay.sprinkling_money.common.secure.SecureRandomGenerator;
import com.kakaopay.sprinkling_money.common.token.TokenConfiguration;
import com.kakaopay.sprinkling_money.domain.Money;
import com.kakaopay.sprinkling_money.domain.token.Token;
import com.kakaopay.sprinkling_money.domain.token.TokenService;
import com.kakaopay.sprinkling_money.domain.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.OffsetDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class DistributionServiceTest {

    @Autowired
    private SecureRandomGenerator secureRandomGenerator;

    @MockBean
    private TokenService tokenService;

    @Autowired
    private DistributionConfiguration distributionConfiguration;

    @Autowired
    private DistributionService distributionService;

    @Autowired
    private TokenConfiguration tokenConfiguration;

    @MockBean
    private DistributionRepository distributionRepository;

    @Test
    public void createDistributionTest() {
        Money totalMoney;
        int targetUserCount;

        String tokenValue;

        Distribution distribution;
        Token token;

        given:
        {
            totalMoney = new Money(1000L);
            targetUserCount = 3;

            tokenValue = secureRandomGenerator.generateRandomString(tokenConfiguration.getMaxLength());
            token = new Token(tokenValue);

            doReturn(token).when(tokenService).generateToken();
        }

        when:
        {
            distribution = distributionService.createDistribution(totalMoney, targetUserCount);
        }

        then:
        {
            assertThat(distribution).isNotNull();
            assertThat(distribution.getId()).isNotNull();
            assertThat(distribution.getTargetMemberCount()).isEqualTo(targetUserCount);
            assertThat(distribution.getMoney()).isEqualTo(totalMoney);
            assertThat(distribution.getExecutionTime()).isNotNull();
            assertThat(distribution.getToken()).isEqualToComparingOnlyGivenFields(token, "value");
            assertThat(distribution.getDistributionInfoList()).isNotEmpty().hasSize(3);
        }
    }

    @Test
    public void inquiryDistributionTest() {
        Distribution result;

        given:
        {
            Distribution distribution = mock(Distribution.class);

            doReturn(true).when(distribution).isDistributor(any(User.class));
            doReturn(Optional.of(distribution)).when(distributionRepository).findCurrentStatus(anyString(), anyString(), any(OffsetDateTime.class));
        }

        when:
        {
            result = distributionService.inquiryDistribution(new User("test"), "roomId", "token");
        }

        then:
        {
            assertThat(result).isNotNull();
        }
    }

}
