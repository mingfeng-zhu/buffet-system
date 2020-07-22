package cn.ffcs.buffet.common.dto;

import cn.ffcs.buffet.common.enums.ExceptionEnum;

/**
 * @Description: 返回结果封装类
 * @Author: mingfeng.zhu@ffcs.cn
 * @Date: 2020/7/21 16:00
 */
@SuppressWarnings("unchecked")
public class Result<T> {


    public final static int SUCCESS_CODE = 2000;

    public final static int FAIL_CODE = 5000;


    /**
     * 状态码
     */
    private Integer code;

    /**
     * 消息提示
     */
    private String message;

    /**
     * 响应数据
     */
    private T data;

    private Result(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 成功时调用
     *
     * @return
     */
    public static Result success() {
        return new Result(SUCCESS_CODE, null, null);
    }

    public static <T> Result success(T data) {
        return new Result(SUCCESS_CODE, "", data);
    }

    public static Result fail(String message) {
        return new Result(FAIL_CODE, message, null);
    }

    public static Result fail(String message, Integer code) {
        return new Result(code, message, null);

    }

    public static Result fail(ExceptionEnum e) {
        return fail(e.getMsg(), e.getCode());

    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


}
