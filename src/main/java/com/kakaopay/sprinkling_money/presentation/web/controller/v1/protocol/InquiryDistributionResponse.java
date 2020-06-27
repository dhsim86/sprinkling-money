package com.kakaopay.sprinkling_money.presentation.web.controller.v1.protocol;

import com.kakaopay.sprinkling_money.domain.distribution.Distribution;
import com.kakaopay.sprinkling_money.domain.distribution.DistributionInfo;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class InquiryDistributionResponse {

    @Getter
    public static class DistributedUserMoney {

        private int userId;
        private Long moneyAmount;

        public DistributedUserMoney(DistributionInfo distributionInfo) {
            this.userId = distributionInfo.getUserId();
            this.moneyAmount = distributionInfo.getMoney().getAmount();
        }
    }

    private OffsetDateTime executionTime;

    private Long totalMoney;
    private Long receivedMoney;

    private List<DistributedUserMoney> distributedUserMoneyList;

    public InquiryDistributionResponse(Distribution distribution) {
        executionTime = distribution.getExecutionTime();
        totalMoney = distribution.getMoney().getAmount();

        distributedUserMoneyList = distribution.getDistributionInfoList().stream()
                .filter(distributionInfo -> distributionInfo.isReceived())
                .map(DistributedUserMoney::new)
                .collect(Collectors.toList());

        receivedMoney = distributedUserMoneyList.stream()
                .map(DistributedUserMoney::getMoneyAmount)
                .reduce(0L, (a, b) -> a + b);
    }

}
