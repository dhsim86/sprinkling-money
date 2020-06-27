package com.kakaopay.sprinkling_money.presentation.web.controller.v1;

import com.kakaopay.sprinkling_money.domain.user.UserService;
import com.kakaopay.sprinkling_money.presentation.web.controller.v1.protocol.RegisterUserRequest;
import com.kakaopay.sprinkling_money.presentation.web.controller.v1.protocol.RegisterUserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1.0/users")
public class UserController {

    @Autowired
    private UserService userService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public RegisterUserResponse registerUser(@Valid @RequestBody RegisterUserRequest request) {
        return new RegisterUserResponse(userService.registerUser(request.getName()));
    }

}
