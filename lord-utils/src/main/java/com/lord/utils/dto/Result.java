package com.lord.utils.dto;

import java.io.Serializable;

/**
 * 功能：接口的返回信息说明
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年03月2017/3/30日
 */
public class Result implements Serializable {
    private int code = Code.ReqSuccess.getCode();
    private String msg = Code.ReqSuccess.getMsg();
    private boolean success = true;
    private Object data;

    public Result(){}

    public Result(Code code) {
        if (code == null) return;
        this.code = code.getCode();
        if (!Code.ReqSuccess.equals(code)) {
            this.success = false;
        }
        this.msg = code.getMsg();
    }

    public Result(Code code, String msg) {
        if (code == null) return;
        this.code = code.getCode();
        if (!Code.ReqSuccess.equals(code)) {
            this.success = false;
        }
        this.msg = msg;
    }

    public Result(Code code, String msg, Object data) {
        if (code == null) return;
        this.code = code.getCode();
        if (!Code.ReqSuccess.equals(code)) {
            this.success = false;
        }
        this.msg = msg;
        this.data = data;
    }

    public static Result success() {
        return new Result(Code.ReqSuccess);
    }

    public static Result success(String msg) {
        return new Result(Code.ReqSuccess, msg, null);
    }

    public static Result success(Object data) {
        return new Result(Code.ReqSuccess, "成功", data);
    }

    public static Result success(String msg, Object data) {
        return new Result(Code.ReqSuccess, msg, data);
    }

    public static Result failure() {
        return new Result(Code.ReqFailure);
    }

    public static Result failure(String msg) {
        return new Result(Code.ReqFailure, msg, null);
    }

    public static Result failure(String msg, Object data) {
        return new Result(Code.ReqFailure, msg, data);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
