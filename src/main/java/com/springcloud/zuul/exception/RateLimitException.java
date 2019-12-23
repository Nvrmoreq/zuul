package com.springcloud.zuul.exception;

import com.springcloud.zuul.enums.ResultEnum;

/**
 * @Author: ZQ
 * @Description:
 * @Date created in 16:05 2019/12/20
 */
public class RateLimitException extends RuntimeException {

    private Integer code;

    private String message;

    public RateLimitException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }
}
