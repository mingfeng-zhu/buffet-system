package cn.ffcs.buffet.service;


import cn.ffcs.buffet.common.dto.Page;
import cn.ffcs.buffet.model.po.UserPO;
import com.github.pagehelper.PageInfo;


/**
 * @Description: user
 * @Author: wx.zhu@ffcs.cn
 * @Date: 2020/8/5 21:57
 */
public interface UserService {

    /**
     * 登录
     *
     * @param user
     * @return
     */
    UserPO checkLogin(UserPO user);

    /**
     * 注册
     *
     * @param user
     * @return
     */
    Integer signUpUser(UserPO user);

    /**
     * 修改
     * @param user
     * @return
     */
    Integer updateUser(UserPO user);

    /**
     * 获取登录用户信息
     * @return
     */
    UserPO getUserInfo();

    /**
     * 获取分页list
     * @param page 分页
     * @param username 用户名
     * @param userState 用户状态
     * @return
     */
    PageInfo<UserPO> listUser(Page page, String username,Integer userState);
}