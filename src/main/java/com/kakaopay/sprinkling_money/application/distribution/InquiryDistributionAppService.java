package com.kakaopay.sprinkling_money.application.distribution;

import com.kakaopay.sprinkling_money.domain.distribution.Distribution;
import com.kakaopay.sprinkling_money.domain.distribution.DistributionRepository;
import com.kakaopay.sprinkling_money.domain.distribution.DistributionService;
import com.kakaopay.sprinkling_money.domain.user.User;
import com.kakaopay.sprinkling_money.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InquiryDistributionAppService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DistributionRepository distributionRepository;

    @Autowired
    private DistributionService distributionService;

    public Distribution inquiryDistribution(int userId, String roomId, String tokenValue) {
        User user = userRepository.getOne(userId);
        Distribution result = distributionService.inquiryDistribution(user, roomId, tokenValue);
        return result;
    }

}
