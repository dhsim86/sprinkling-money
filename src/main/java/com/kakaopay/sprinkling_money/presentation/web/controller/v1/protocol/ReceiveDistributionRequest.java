package com.kakaopay.sprinkling_money.presentation.web.controller.v1.protocol;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class ReceiveDistributionRequest {

    @Pattern(regexp = "^[a-zA-Z0-9_.-]{3}$", message = "The token is malformed.")
    @NotBlank(message = "the token is necessary.")
    private String token;

}
