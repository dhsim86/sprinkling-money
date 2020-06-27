package com.kakaopay.sprinkling_money.domain.distribution.exception;

import com.kakaopay.sprinkling_money.common.exception.CommonException;
import com.kakaopay.sprinkling_money.domain.distribution.exception.code.DistributionExceptionCode;
import lombok.Getter;

@Getter
public class DistributionException extends CommonException {

    private static final long serialVersionUID = -4373022472692056340L;

    private boolean isServerError;

    public DistributionException(DistributionExceptionCode code, Object... parameter) {
        super(code.buildMessage(parameter));
        this.isServerError = code.isServerError();
    }

    public DistributionException(Throwable ex, DistributionExceptionCode code, Object... parameter) {
        super(code.buildMessage(parameter), ex);
        this.isServerError = code.isServerError();
    }

}
