package com.kakaopay.sprinkling_money.domain.token.exception;

import com.kakaopay.sprinkling_money.common.exception.CommonException;
import com.kakaopay.sprinkling_money.domain.token.exception.code.TokenExceptionCode;

public class TokenException extends CommonException {

    private static final long serialVersionUID = 4817573723214520263L;

    public TokenException(TokenExceptionCode code, Object... parameter) {
        super(code.buildMessage(parameter));
    }

}
