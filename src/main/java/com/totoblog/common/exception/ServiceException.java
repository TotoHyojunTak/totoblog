package com.totoblog.common.exception;

import com.sun.nio.sctp.IllegalUnbindException;
import com.totoblog.common.dto.CommonResponseDTO;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.IllegalFormatException;

import static com.totoblog.common.dto.ResCode.*;

/*** 서비스 작동 중 발생하는 에러 ***/
@Getter
@Slf4j
public class ServiceException {

    /*** 요청 파라미터 오류 ***/
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({IllegalArgumentException.class, BindException.class, IllegalFormatException.class, IllegalUnbindException.class})
    public CommonResponseDTO handleIllegalArgumentException(Exception e) {
        log.error("handleIllegalArgumentException: ", e);
        return CommonResponseDTO.of(ERR_PARAMETER);
    }

    /*** 시스템 에러 발생 ***/
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(java.lang.Exception.class)
    public CommonResponseDTO handleException(Exception e) {
        log.error("handleException: ", e);
        return CommonResponseDTO.of(ERR_SYSTEM);
    }
}