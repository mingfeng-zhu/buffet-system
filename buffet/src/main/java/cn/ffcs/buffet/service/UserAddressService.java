package cn.ffcs.buffet.service;

import cn.ffcs.buffet.model.po.UserAddress;
import cn.ffcs.buffet.model.po.UserPO;

import java.util.List;

/**
 * @Description: user
 * @Author: wx.zhu@ffcs.cn
 * @Date: 2020/8/5 21:57
 */
public interface UserAddressService {
    /**
     * 查询用户配置的送货地址
     * @param userId
     * @return
     */
    List<UserAddress> selectUserAddress(Integer userId);

    /**
     * 新增用户送货地址
     * @param userAddress
     * @return
     */
    Integer addUserAddress(UserAddress userAddress);

    /**
     * 修改用户送货地址
     * @param userAddress
     * @return
     */
    Integer updateUserAddress(UserAddress userAddress);
}
