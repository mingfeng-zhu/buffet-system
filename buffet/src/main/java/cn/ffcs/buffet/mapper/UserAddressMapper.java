package cn.ffcs.buffet.mapper;

import cn.ffcs.buffet.model.po.UserAddress;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description: 用户地址映射类
 * @Author: wx.zhu@ffcs.cn
 * @Date: 2020/8/5 21:50
 */
@Component
public interface UserAddressMapper {
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
    Integer updateUserAddress9(UserAddress userAddress);
}
