package com.kakaopay.sprinkling_money.application.distribution;

import com.kakaopay.sprinkling_money.domain.Money;
import com.kakaopay.sprinkling_money.domain.distribution.Distribution;
import com.kakaopay.sprinkling_money.domain.distribution.DistributionRepository;
import com.kakaopay.sprinkling_money.domain.distribution.exception.DistributionException;
import com.kakaopay.sprinkling_money.domain.distribution.exception.code.DistributionExceptionCode;
import com.kakaopay.sprinkling_money.domain.user.User;
import com.kakaopay.sprinkling_money.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReceiveDistributionAppService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DistributionRepository distributionRepository;

    @Transactional
    public Money receiveDistribution(int userId, String roomId, String tokenValue) {
        User user = userRepository.getOne(userId);

        Distribution distribution = distributionRepository.findByRoomIdAndTokenValue(roomId, tokenValue)
                .orElseThrow(() -> new DistributionException(DistributionExceptionCode.DISTRIBUTION_NOT_FOUND, roomId, tokenValue));

        return distribution.distribute(user);
    }

}
