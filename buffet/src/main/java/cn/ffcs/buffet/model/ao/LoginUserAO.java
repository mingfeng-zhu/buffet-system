package cn.ffcs.buffet.model.ao;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @Description: 登录、注册用户AO
 * @Author: mingfeng.zhu@ffcs.cn
 * @Date: 2020/8/22 22:02
 */
@ApiModel(value = "/api/user/login参数")
public class LoginUserAO implements Serializable {
    private static final long serialVersionUID = -3412183826992634165L;

    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号码", name = "userTel", required = true, example = "18250186106")
    private String userTel;

    /**
     * 用户密码
     */
    @ApiModelProperty(value = "用户密码", name = "userPassword", required = true, example = "******")
    private String userPassword;

    /**
     * code验证码
     */
    private String code;

    public String getUserTel() {
        return userTel;
    }

    public void setUserTel(String userTel) {
        this.userTel = userTel;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "LoginUserAO{" +
                "userTel='" + userTel + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
