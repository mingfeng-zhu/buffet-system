package cn.ffcs.buffet.model.ao;

import cn.ffcs.buffet.model.po.UserPO;

import java.io.Serializable;

/**
 * @Description: 登录成功AO
 * @Author: mingfeng.zhu@ffcs.cn
 * @Date: 2020/8/17 17:32
 */
public class UserAO implements Serializable {

    private static final long serialVersionUID = -4453980608626158206L;

    /**
     * 用户信息
     */
    private UserPO userPO;

    /**
     * token
     */
    private String token;

    public UserPO getUserPO() {
        return userPO;
    }

    public void setUserPO(UserPO userPO) {
        this.userPO = userPO;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
