package com.kakaopay.sprinkling_money.presentation.web.controller.v1.protocol;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
public class InviteUsersRequest {

    @Length(max = 64, message = "The length of name must be lesser than {max}.")
    @NotBlank(message = "The name of room is necessary.")
    private String name;

    @NotEmpty(message = "The list of user is necessary.")
    private List<Integer> userIdList;

}
