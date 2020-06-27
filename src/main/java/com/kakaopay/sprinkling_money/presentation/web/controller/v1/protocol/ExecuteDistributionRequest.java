package com.kakaopay.sprinkling_money.presentation.web.controller.v1.protocol;

import com.kakaopay.sprinkling_money.domain.Money;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ExecuteDistributionRequest {

    @NotNull(message = "The number of target user for distribution is necessary.")
    @Min(value = 1, message = "The number should be greater than zero.")
    private Integer targetUserCount;

    @Getter(AccessLevel.NONE)
    @NotNull(message = "The amount of money for distribution is necessary.")
    @Min(value = 1, message = "The number should be greater than zero.")
    private Long moneyAmount;

    public Money getMoneyAmount() {
        return new Money(moneyAmount);
    }

}
