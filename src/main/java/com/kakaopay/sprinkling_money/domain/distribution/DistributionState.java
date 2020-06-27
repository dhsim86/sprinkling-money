package com.kakaopay.sprinkling_money.domain.distribution;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum DistributionState {

    NORMAL(""),
    NOT_TARGET("The user is not target for distribution"),
    EXPIRED("The distribution is expired"),
    EXHAUSTED("There is no distribution sharing"),
    ALREADY_RECEIVED("Already received");

    private String message;
}
