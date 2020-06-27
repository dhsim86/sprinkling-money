package com.kakaopay.sprinkling_money.common.exception;

public class CommonException extends RuntimeException {

    private static final long serialVersionUID = 7069128312354335449L;

    public CommonException(String message) {
        super(message);
    }

    public CommonException(String message, Throwable cause) {
        super(message, cause);
    }

}
