package com.kakaopay.sprinkling_money.presentation.web.controller.v1;

import com.kakaopay.sprinkling_money.application.room.RoomInvitationAppService;
import com.kakaopay.sprinkling_money.presentation.web.controller.v1.protocol.InviteUsersRequest;
import com.kakaopay.sprinkling_money.presentation.web.controller.v1.protocol.InviteUsersResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.kakaopay.sprinkling_money.presentation.web.controller.v1.config.RequestHeaderConfig.USER_ID_HEADER_NAME;

@RestController
@RequestMapping("/api/v1.0/rooms")
public class RoomController {

    @Autowired
    private RoomInvitationAppService roomInvitationAppService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public InviteUsersResponse inviteUsers(@RequestHeader(USER_ID_HEADER_NAME) Integer userId,
                                           @Valid @RequestBody InviteUsersRequest request) {
        return new InviteUsersResponse(roomInvitationAppService.inviteRoom(userId, request.getName(), request.getUserIdList()));
    }

}
