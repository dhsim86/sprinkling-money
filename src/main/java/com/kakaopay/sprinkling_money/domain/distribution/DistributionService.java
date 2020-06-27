package com.kakaopay.sprinkling_money.domain.distribution;

import com.kakaopay.sprinkling_money.common.distribution.DistributionConfiguration;
import com.kakaopay.sprinkling_money.common.secure.SecureRandomGenerator;
import com.kakaopay.sprinkling_money.domain.Money;
import com.kakaopay.sprinkling_money.domain.distribution.exception.DistributionException;
import com.kakaopay.sprinkling_money.domain.distribution.exception.code.DistributionExceptionCode;
import com.kakaopay.sprinkling_money.domain.token.TokenService;
import com.kakaopay.sprinkling_money.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class DistributionService {

    @Autowired
    private SecureRandomGenerator secureRandomGenerator;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private DistributionConfiguration distributionConfiguration;

    @Autowired
    private DistributionRepository distributionRepository;

    public OffsetDateTime getExpireTime() {
        return OffsetDateTime.now().minusDays(distributionConfiguration.getValidDay());
    }

    public Distribution createDistribution(Money amount, int targetUserCount) {
        List<Money> moneyList = amount.distribute(secureRandomGenerator, targetUserCount);
        return Distribution.newInstance(targetUserCount, amount, tokenService.generateToken(), moneyList);
    }

    public Distribution inquiryDistribution(User user, String roomId, String tokenValue) {
        OffsetDateTime expireDateTime = OffsetDateTime.now().minusDays(distributionConfiguration.getValidDay());

        return distributionRepository.findCurrentStatus(roomId, tokenValue, expireDateTime)
                .filter(distribution -> distribution.isDistributor(user))
                .orElseThrow(() -> new DistributionException(DistributionExceptionCode.DISTRIBUTION_INVALID, user.getId(), roomId, tokenValue));
    }

}
