package cn.ffcs.buffet.mapper;

import cn.ffcs.buffet.model.po.UserPO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;


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
}