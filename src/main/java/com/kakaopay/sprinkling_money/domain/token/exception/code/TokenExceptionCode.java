package com.kakaopay.sprinkling_money.domain.token.exception.code;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum TokenExceptionCode {

    TOKEN_ISSUE_FAILED("Trying to issuing token failed.");

    private String message;

    public String buildMessage(Object... objects) {
        return String.format(message, objects);
    }

}
