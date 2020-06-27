package com.kakaopay.sprinkling_money.presentation.web.controller.error;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorMessageCode {

    SUCCESS("Success"),

    INVALID_REQUEST("Invalid Request. cause: '%s'"),
    INVALID_REQUEST_PARAMETER("Invalid request parameter. name: '%s', value: '%s', cause: '%s'"),
    INVALID_REQUEST_HEADER("Invalid request header. headerName: '%s'"),

    INTERNAL_SERVER_ERROR("Internal server error. Contact server operator please.");

    private String message;

    public String buildMessage(Object... objects) {
        return String.format(message, objects);
    }

}
