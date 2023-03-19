package com.totoblog.common.exception;

import com.totoblog.common.dto.ResCode;
import lombok.Getter;

/*** Custom 지정 Exception 처리 구현***/
@Getter
public class Exception extends RuntimeException {

    private String errCd;
    private String errMsg;

    public Exception(final ResCode resCode) {
        super(resCode.getMessage());
        this.errCd = resCode.getCode();
        this.errMsg = resCode.getMessage();
    }
}