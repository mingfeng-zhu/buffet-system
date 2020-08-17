package cn.ffcs.buffet.model.dto;

/**
 * @Description: 登录用户DTO
 * @Author: mingfeng.zhu@ffcs.cn
 * @Date: 2020/8/17 17:53
 */
public class UserDTO {
    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 用户tel
     */
    private String userTel;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserTel() {
        return userTel;
    }

    public void setUserTel(String userTel) {
        this.userTel = userTel;
    }
}
