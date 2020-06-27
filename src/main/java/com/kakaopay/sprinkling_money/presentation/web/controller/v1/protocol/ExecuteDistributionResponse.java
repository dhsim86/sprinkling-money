package com.kakaopay.sprinkling_money.presentation.web.controller.v1.protocol;

import com.kakaopay.sprinkling_money.domain.token.Token;
import lombok.Getter;

@Getter
public class ExecuteDistributionResponse {

    private String token;

    public ExecuteDistributionResponse(Token token) {
        this.token = token.getValue();
    }

}
