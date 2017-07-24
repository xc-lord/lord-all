package com.lord.utils.exception;

import com.lord.utils.dto.Code;

/**
 * 功能：公用的运行时错误
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年03月2017/3/30日
 */
public class CommonException extends RuntimeException {
    /** 错误码 */
    private int code = Code.ReqFailure.getCode();
    /** 错误信息 */
    private String msg = Code.ReqFailure.getMsg();

    public CommonException(Code code) {
        super("[" + code.getCode() + ":" + code.toString() +"]" + code.getMsg());
        this.code = code.getCode();
        this.msg = code.getMsg();
    }

    public CommonException(String msg) {
        super("[" + Code.ReqFailure.getCode() + ":" + Code.ReqFailure.toString() +"]" + msg);
        this.code = Code.ReqFailure.getCode();
        this.msg = msg;
    }

    public CommonException(Code code, String msg) {
        super("[" + code.getCode() + ":" + code.toString() +"]" + msg);
        this.code = code.getCode();
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
