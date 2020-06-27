package com.kakaopay.sprinkling_money.presentation.web.controller.v1.protocol;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class RegisterUserRequest {

    @Length(max = 64, message = "The length of name must be lesser than {max}.")
    @NotBlank(message = "The name of user is necessary.")
    private String name;

}
