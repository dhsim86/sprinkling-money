package com.kakaopay.sprinkling_money.domain.user.exception.code;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum UserExceptionCode {

    USER_REGISTER_FAILED("Failed to persist user. user name: '%s'"),
    USER_NOT_JOINED_ROOM("Not joined room. userId: '%s', roomId: '%s'");

    private String message;

    public String buildMessage(Object... objects) {
        return String.format(message, objects);
    }

}
