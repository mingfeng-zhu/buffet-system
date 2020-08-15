package cn.ffcs.buffet.service.impl;

import cn.ffcs.buffet.mapper.UserAddressMapper;
import cn.ffcs.buffet.model.po.UserAddress;
import cn.ffcs.buffet.service.UserAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


/**
 * @Description: user
 * @Author: wx.zhu@ffcs.cn
 * @Date: 2020/8/5 22:59
 */
@Service
public class UserAddressServiceImpl implements UserAddressService {

    @Autowired
    private UserAddressMapper mapper;
    /**
     * 查询用户配置的送货地址
     * @param userId
     * @return
     */
    @Override
    public List<UserAddress> selectUserAddress(Integer userId) {
        return mapper.selectUserAddress(userId);
    }
    /**
     * 新增用户送货地址
     * @param userAddress
     * @return
     */
    @Override
    public Integer addUserAddress(UserAddress userAddress) {
        userAddress.setCreateTime(new Date());
        return mapper.addUserAddress(userAddress);
    }
    /**
     * 修改用户送货地址
     * @param userAddress
     * @return
     */
    @Override
    public Integer updateUserAddress(UserAddress userAddress) {
        userAddress.setUpdateTime(new Date());
        return mapper.updateUserAddress9(userAddress);
    }
}
