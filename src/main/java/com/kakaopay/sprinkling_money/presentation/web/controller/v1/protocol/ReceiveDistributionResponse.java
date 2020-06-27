package com.kakaopay.sprinkling_money.presentation.web.controller.v1.protocol;

import com.kakaopay.sprinkling_money.domain.Money;
import lombok.Getter;

@Getter
public class ReceiveDistributionResponse {

    private Long moneyAmount;

    public ReceiveDistributionResponse(Money money) {
        this.moneyAmount = money.getAmount();
    }

}
