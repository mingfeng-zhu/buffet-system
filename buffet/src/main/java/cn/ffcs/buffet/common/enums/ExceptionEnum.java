package cn.ffcs.buffet.common.enums;

/**
 * @Description: 自定义枚举类
 * @Author: mingfeng.zhu@ffcs.cn
 * @Date: 2020/7/21 16:10
 */
public enum ExceptionEnum {
    /**
     * 用户被禁用
     */
    USER_FORBIDDEN(1001, "用户被禁用"),

    /**
     * 用户不存在
     */
    USER_NOT_EXIST(1002, "用户不存在"),

    /**
     * 用户已存在，无法注册
     */
    USER_IS_EXIST(1003, "用户已存在，无法注册"),

    /**
     * 用户密码错误
     */
    USER_PASSWORD_ERROR(1004, "用户密码错误"),

    /**
     * 验证码不正确
     */
    CAPTCHA_ERROR(1005, "验证码不正确"),

    /**
     * token已过期
     */
    TOKEN_EXPIRED(1006, "token已过期"),

    /**
     * token不存在
     */
    TOKEN_NOT_EXIST(1007, "token不存在");


    /**
     * 状态码
     */
    private Integer code;

    /**
     * 异常消息
     */
    private String msg;

    /**
     * 构造器
     *
     * @param code code
     * @param msg  异常信息
     */
    private ExceptionEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getMsg() {
        return this.msg;
    }

    public Integer getCode() {
        return this.code;
    }
}

