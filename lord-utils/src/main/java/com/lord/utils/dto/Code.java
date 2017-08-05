package com.lord.utils.dto;

/**
 * 功能：系统的返回状态码和错误码说明
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年03月2017/3/30日
 */
public enum Code {
    //系统状态码
    ReqSuccess(1, "执行成功"),
    ReqNotData(2, "没有记录"),
    ReqLostParams(3, "参数为空或不完整"),
    ReqFailure(4, "执行失败"),
    ReqIsExist(5, "已存在相同的记录"),

    //用户和接口权限状态码
    ApiNotLogin(401, "用户未登录"),
    ApiForbidden(403, "请求被禁止"),
    ApiNotFound(404, "API接口不存在"),
    ApiStop(405, "API已停用"),
    ApiUnavailable(406, "服务不可用"),
    ApiTimeOut(407, "请求超时"),
    ApiNotPermissions(408, "用户权限不足"),
    ApiCallLimited(409, "超过调用次数"),
    ApiInvalidSign(410, "签名错误"),

    //常见错误码
    ErrorSystem(500, "服务器内部错误"),
    ErrorFormat(501, "格式错误"),
    ErrorType(502, "类型错误"),
    ErrorOverLength(503, "超过规定长度"),
    ErrorEmail(504, "邮箱格式错误"),
    ErrorPhone(505, "手机号码格式错误"),
    ErrorPageCode(506, "图形验证码错误"),
    ErrorPhoneCode(507, "手机验证码错误"),

    //业务级别错误码
    SmsOrIPLimited(1101, "号码或ip距离上次发送的消息的时间太短"),
    SmsPhoneDayLimited(1102, "该号码今天的发送记录已经超过最大次数"),
    SmsIpDayLimited(1103, "该ip今天的发送记录已经超过最大次数");

    private int code;
    private String msg;

    Code(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
