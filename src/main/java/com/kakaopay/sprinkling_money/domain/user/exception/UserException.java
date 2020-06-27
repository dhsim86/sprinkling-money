package com.kakaopay.sprinkling_money.domain.user.exception;

import com.kakaopay.sprinkling_money.common.exception.CommonException;
import com.kakaopay.sprinkling_money.domain.user.exception.code.UserExceptionCode;

public class UserException extends CommonException {

    private static final long serialVersionUID = 1661889460470142693L;

    public UserException(UserExceptionCode code, Object... parameter) {
        super(code.buildMessage(parameter));
    }

    public UserException(Throwable ex, UserExceptionCode code, Object... parameter) {
        super(code.buildMessage(parameter), ex);
    }

}
