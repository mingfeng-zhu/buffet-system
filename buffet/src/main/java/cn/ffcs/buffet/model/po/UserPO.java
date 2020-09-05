package cn.ffcs.buffet.model.po;

import cn.ffcs.buffet.common.util.Constant;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;

import java.util.Date;

@ApiModel
public class UserPO {

//    user_id	int	是	否	用户id
//    user_name	varchar	否	否	用户姓名
//    user_tel	varchar	否	否	手机号
//    create_time	datetime	否	否	创建时间
//    update_time	datetime	否	是	更新时间
//    user_state	Int	否	否	用户状态
//    user_img	varchar	否	是	用户头像
//    user_sex	Int	否	否	用户性别(1-男0-女)
//    user_password	Varchar	否	否	用户密码
    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 用户姓名
     */
    private String userName;

    /**
     * 手机号
     */
    private String userTel;

    /**
     * 创建时间
     */
    private Date createTime = new Date();
    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 修改时间
     */
    private Integer userState = Constant.USER_STATUS.nomal.getIndex();

    /**
     * 用户头像
     */
    private String userImg;

    /**
     * 用户性别
     */
    private Integer userSex;

    /**
     * 用户密码
     */
    private String userPassword;

    /**
     * 默认地址
     */
    private Integer defaultAddressId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserTel() {
        return userTel;
    }

    public void setUserTel(String userTel) {
        this.userTel = userTel;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getUserState() {
        return userState;
    }

    public void setUserState(Integer userState) {
        this.userState = userState;
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

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public Integer getDefaultAddressId() {
        return defaultAddressId;
    }

    public void setDefaultAddressId(Integer defaultAddressId) {
        this.defaultAddressId = defaultAddressId;
    }

    @Override
    public String toString() {
        return "UserPO{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userTel='" + userTel + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", userState=" + userState +
                ", userImg='" + userImg + '\'' +
                ", userSex=" + userSex +
                ", userPassword='" + userPassword + '\'' +
                '}';
    }
}