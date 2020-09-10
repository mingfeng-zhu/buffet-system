package cn.ffcs.buffet.mapper;

import cn.ffcs.buffet.model.po.UserPO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.math.BigDecimal;

/**
 * @Description: 用户映射类
 * @Author: wx.zhu@ffcs.cn
 * @Date: 2020/8/5 21:29
 */
@Component
public interface UserPOMapper {

    UserPO checkLogin(UserPO user);

    int signUpUser(UserPO user);

    int updateUser(UserPO user);

    UserPO selectUserByUserId(@Param("userId") Integer userId);

    /**
     * 管理员 获取用户列表
     * @param username
     * @param userState
     * @return
     */
    List<UserPO> listUser(@Param("username")String username,@Param("userState")Integer userState);

    Integer updateMoneyByUser(Integer userId, BigDecimal totalMoney);

}