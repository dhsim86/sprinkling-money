package com.kakaopay.sprinkling_money.presentation.web.controller.v1;

import com.kakaopay.sprinkling_money.application.distribution.ExecuteDistributionAppService;
import com.kakaopay.sprinkling_money.application.distribution.InquiryDistributionAppService;
import com.kakaopay.sprinkling_money.application.distribution.ReceiveDistributionAppService;
import com.kakaopay.sprinkling_money.presentation.web.controller.v1.protocol.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.kakaopay.sprinkling_money.presentation.web.controller.v1.config.RequestHeaderConfig.ROOM_ID_HEADER_NAME;
import static com.kakaopay.sprinkling_money.presentation.web.controller.v1.config.RequestHeaderConfig.USER_ID_HEADER_NAME;

@RestController
@RequestMapping("/api/v1.0/distributions")
public class DistributionController {

    @Autowired
    private ExecuteDistributionAppService executeDistributionAppService;

    @Autowired
    private ReceiveDistributionAppService receiveDistributionAppService;

    @Autowired
    private InquiryDistributionAppService inquiryDistributionAppService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("execute")
    public ExecuteDistributionResponse executeDistribution(
            @RequestHeader(USER_ID_HEADER_NAME) Integer userId,
            @RequestHeader(ROOM_ID_HEADER_NAME) String roomId,
            @Valid @RequestBody ExecuteDistributionRequest request) {
        return new ExecuteDistributionResponse(executeDistributionAppService.ExecuteDistribution(
                userId,
                roomId,
                request.getMoneyAmount(),
                request.getTargetUserCount()));
    }

    @PostMapping("receive")
    public ReceiveDistributionResponse receiveDistribution(
            @RequestHeader(USER_ID_HEADER_NAME) Integer userId,
            @RequestHeader(ROOM_ID_HEADER_NAME) String roomId,
            @Valid @RequestBody ReceiveDistributionRequest request) {
        return new ReceiveDistributionResponse(receiveDistributionAppService.receiveDistribution(
                userId,
                roomId,
                request.getToken()));
    }

    @GetMapping
    public InquiryDistributionResponse inquiryDistribution(
            @RequestHeader(USER_ID_HEADER_NAME) Integer userId,
            @RequestHeader(ROOM_ID_HEADER_NAME) String roomId,
            @Valid @ModelAttribute InquiryDistributionRequest request) {
        return new InquiryDistributionResponse(inquiryDistributionAppService.inquiryDistribution(
                userId,
                roomId,
                request.getToken()));
    }

}
