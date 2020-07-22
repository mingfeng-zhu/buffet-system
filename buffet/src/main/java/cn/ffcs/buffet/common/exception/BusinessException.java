package cn.ffcs.buffet.common.exception;

import cn.ffcs.buffet.common.enums.ExceptionEnum;

/**
 * @Description: 业务异常
 * @Author: mingfeng.zhu@ffcs.cn
 * @Date: 2020/7/21 10:29
 */
public class BusinessException extends RuntimeException {

    /**
     * code
     */
    private int code;
    /**
     * 异常信息
     */
    private String msg;

    /**
     * 空参构造方法
     */
    public BusinessException() {
    }

    /**
     * api异常构造方法
     * @param exceptionEnum 异常枚举类
     */
    public BusinessException(ExceptionEnum exceptionEnum) {
        super(exceptionEnum.getMsg());
        this.code = exceptionEnum.getCode();
        this.msg = exceptionEnum.getMsg();
    }

    /**
     * 异常信息传入
     * @param code
     * @param msg
     */
    public BusinessException(int code,String msg){
        super(msg);
        this.code=code;
        this.msg=msg;
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

    @Override
    public String toString() {
        return "BusinessException{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}

