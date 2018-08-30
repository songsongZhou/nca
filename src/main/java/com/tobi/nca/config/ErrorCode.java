package com.tobi.nca.config;

import com.baomidou.mybatisplus.extension.api.IErrorCode;

public enum ErrorCode implements IErrorCode {
    TEST("1000", "测试错误编码"),EMPTY("1001","参数不能为空");

    private String code;
    private String msg;

    ErrorCode(final String code, final String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }
}
