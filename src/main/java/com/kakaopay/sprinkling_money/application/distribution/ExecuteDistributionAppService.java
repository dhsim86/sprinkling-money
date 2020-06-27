package com.kakaopay.sprinkling_money.application.distribution;

import com.kakaopay.sprinkling_money.domain.Money;
import com.kakaopay.sprinkling_money.domain.distribution.Distribution;
import com.kakaopay.sprinkling_money.domain.distribution.DistributionRepository;
import com.kakaopay.sprinkling_money.domain.distribution.DistributionService;
import com.kakaopay.sprinkling_money.domain.distribution.exception.DistributionException;
import com.kakaopay.sprinkling_money.domain.distribution.exception.code.DistributionExceptionCode;
import com.kakaopay.sprinkling_money.domain.room.Room;
import com.kakaopay.sprinkling_money.domain.token.Token;
import com.kakaopay.sprinkling_money.domain.user.User;
import com.kakaopay.sprinkling_money.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExecuteDistributionAppService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DistributionRepository distributionRepository;

    @Autowired
    private DistributionService distributionService;

    public Token ExecuteDistribution(int userId, String roomId, Money totalMoney, int targetUserCount) {
        try {
            User user = userRepository.getOne(userId);
            Room room = user.findRoom(roomId);

            Distribution distribution = distributionService.createDistribution(totalMoney, targetUserCount);
            distribution.relate(user, room);

            distributionRepository.save(distribution);
            return distribution.getToken();
        } catch (Exception ex) {
            throw new DistributionException(ex, DistributionExceptionCode.DISTRIBUTION_EXECUTE_FAILED, ex.getMessage());
        }
    }

}
