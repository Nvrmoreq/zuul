package com.springcloud.zuul.enums;

import lombok.Getter;

/**
 * @Author: ZQ
 * @Description:
 * @Date created in 16:07 2019/12/20
 */
@Getter
public enum ResultEnum {
    RATE_LIMIT_EXCEPTION_ENUM("获取不到令牌", 401);
    private Integer code;

    private String message;

    ResultEnum(String message, Integer code) {
        this.message = message;
        this.code = code;
    }
}
