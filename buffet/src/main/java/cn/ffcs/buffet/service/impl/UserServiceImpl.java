package cn.ffcs.buffet.service.impl;

import cn.ffcs.buffet.common.dto.Page;
import cn.ffcs.buffet.common.util.TokenUtil;
import cn.ffcs.buffet.mapper.UserPOMapper;
import cn.ffcs.buffet.model.po.UserPO;
import cn.ffcs.buffet.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Description: user
 * @Author: wx.zhu@ffcs.cn
 * @Date: 2020/8/5 21:59
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserPOMapper mapper;

    @Override
    public UserPO checkLogin(UserPO user) {
        if ("admin".equals(user.getUserTel())) {
            user.setUserRole((byte) 1);
        }
        return mapper.checkLogin(user);
    }

    @Override
    public Integer signUpUser(UserPO user) {
        user.setCreateTime(new Date());
        user.setUserState(1);
        return mapper.signUpUser(user);
    }

    @Override
    public Integer updateUser(UserPO user) {
        user.setUpdateTime(new Date());
        return mapper.updateUser(user);
    }

    /**
     * 获取登录用户信息
     *
     * @return
     */
    @Override
    public UserPO getUserInfo() {
        return mapper.selectUserByUserId(TokenUtil.getUserIdAndUserTelOfToken().getUserId());
    }


    @Override
    public PageInfo<UserPO> listUser(Page page, String userTel,Integer userState) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        List<UserPO> list = mapper.listUser(userTel, userState);
        //处理手机号
        for (UserPO user : list) {
            user.setUserTel(new StringBuffer(user.getUserTel()).replace(3, 7, "****").toString());
        }
        PageInfo<UserPO> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }
}