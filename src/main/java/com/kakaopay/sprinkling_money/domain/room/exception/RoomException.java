package com.kakaopay.sprinkling_money.domain.room.exception;

import com.kakaopay.sprinkling_money.common.exception.CommonException;
import com.kakaopay.sprinkling_money.domain.room.exception.code.RoomExceptionCode;

public class RoomException extends CommonException {

    private static final long serialVersionUID = 229906235226364418L;

    public RoomException(Throwable ex, RoomExceptionCode code, Object... parameter) {
        super(code.buildMessage(parameter), ex);
    }
}
