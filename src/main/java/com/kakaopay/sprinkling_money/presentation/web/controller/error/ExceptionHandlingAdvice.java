package com.kakaopay.sprinkling_money.presentation.web.controller.error;

import com.kakaopay.sprinkling_money.common.exception.CommonException;
import com.kakaopay.sprinkling_money.domain.distribution.Distribution;
import com.kakaopay.sprinkling_money.domain.distribution.exception.DistributionException;
import com.kakaopay.sprinkling_money.presentation.web.controller.error.protocol.ErrorMessageResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ServerWebInputException;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlingAdvice {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorMessageResponse handleUnknownException(Exception ex) {
        log.error("Internal Error: {}, {}", ex.getMessage(), ex.getStackTrace());
        return new ErrorMessageResponse(ErrorMessageCode.INTERNAL_SERVER_ERROR.buildMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingRequestHeaderException.class)
    public ErrorMessageResponse handleMissingHeaderException(MissingRequestHeaderException ex) {
        return new ErrorMessageResponse(ErrorMessageCode.INVALID_REQUEST_HEADER.buildMessage(ex.getHeaderName()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ServerWebInputException.class)
    public ErrorMessageResponse handleWebInputException(ServerWebInputException ex) {
        Throwable throwable = ex.getMostSpecificCause();
        return new ErrorMessageResponse(ErrorMessageCode.INVALID_REQUEST.buildMessage(throwable.getMessage()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorMessageResponse handleArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        return new ErrorMessageResponse(ErrorMessageCode.INVALID_REQUEST_PARAMETER.buildMessage(
                result.getFieldError().getField(),
                result.getFieldError().getRejectedValue(),
                result.getFieldError().getDefaultMessage()));
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(CommonException.class)
    public ErrorMessageResponse handleCommonException(CommonException ex) {
        return new ErrorMessageResponse(ex.getMessage());
    }

    @ExceptionHandler(DistributionException.class)
    public ErrorMessageResponse handleInvalidDistributionException(DistributionException ex, HttpServletResponse response) {
        if (ex.isServerError()) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        } else {
            response.setStatus(HttpStatus.NOT_ACCEPTABLE.value());
        }
        return new ErrorMessageResponse(ex.getMessage());
    }

}
