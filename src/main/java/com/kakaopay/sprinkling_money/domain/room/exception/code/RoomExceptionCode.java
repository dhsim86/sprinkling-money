package com.kakaopay.sprinkling_money.domain.room.exception.code;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum RoomExceptionCode {

    ROOM_CREATION_FAILED("Failed to create room. room name: '%s', cause: '%s'");

    private String message;

    public String buildMessage(Object... objects) {
        return String.format(message, objects);
    }

}
