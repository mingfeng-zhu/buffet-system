package cn.ffcs.buffet.model.ao;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @Description: 登录、注册用户AO
 * @Author: mingfeng.zhu@ffcs.cn
 * @Date: 2020/8/22 22:02
 */
@ApiModel(value = "/api/user/signUp参数")
public class SignUserAO implements Serializable {

    private static final long serialVersionUID = 8570247595090481218L;
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
    @ApiModelProperty(value = "用户密码", name = "code", required = true, example = "1234")
    private String code;

    /**
     * 用户姓名
     */
    @ApiModelProperty(value = "用户姓名", name = "userName", required = false, example = "某某某")
    private String userName;

    /**
     * 用户头像
     */
    @ApiModelProperty(value = "用户头像", name = "userImg", required = false, example = "***")
    private String userImg;

    /**
     * 用户性别
     */
    @ApiModelProperty(value = "用户性别 1：女  0：男", name = "userSex", required = false, example = "1")
    private Integer userSex = 1;

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public Integer getUserSex() {
        return userSex;
    }

    public void setUserSex(Integer userSex) {
        this.userSex = userSex;
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
