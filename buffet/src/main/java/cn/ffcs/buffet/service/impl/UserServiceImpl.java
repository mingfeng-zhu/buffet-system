package cn.ffcs.buffet.service.impl;

import cn.ffcs.buffet.mapper.UserPOMapper;
import cn.ffcs.buffet.model.po.UserPO;
import cn.ffcs.buffet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

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
}