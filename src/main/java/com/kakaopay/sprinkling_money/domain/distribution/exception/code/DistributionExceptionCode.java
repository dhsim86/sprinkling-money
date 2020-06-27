package com.kakaopay.sprinkling_money.domain.distribution.exception.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DistributionExceptionCode {

    DISTRIBUTION_EXECUTE_FAILED("Failed to execute distribution. cause: '%s", true),
    DISTRIBUTION_NO_MORE_SHARING("There is no sharing. distributionId: '%s'", false),
    DISTRIBUTION_NOT_FOUND("Not Found distribution. roomId: '%s', token: '%s'", true),
    DISTRIBUTION_INVALID("The condition is abnormal or expired. userId: '%s', roomId: '%s', token: '%s'", true),
    DISTRIBUTION_NOT_RECEIVABLE("The user is not receivable from distribution. userId: '%s', distributionId: '%s", false);

    private String message;
    private boolean isServerError;

    public String buildMessage(Object... objects) {
        return String.format(message, objects);
    }

}
