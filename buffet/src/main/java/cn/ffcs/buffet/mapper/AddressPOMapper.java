package cn.ffcs.buffet.mapper;

import cn.ffcs.buffet.model.po.AddressPO;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description: 地址映射类
 * @Author: mingfeng.zhu
 * @Date: 2020/9/3 19:47
 */
@Component
public interface AddressPOMapper {
    /**
     * 通过id获取地址
     * @param id
     * @return
     */
    AddressPO getAddressById(Integer id);

    /**
     * 通过userId获取这个用户的地址list
     * @param userId
     * @return
     */
    List<AddressPO> getAddressListByUserId(Integer userId);

    /**
     * 插入地址信息
     * @param record
     * @return
     */
    int insert(AddressPO record);

    /**
     * 更新地址信息
     * @param record
     * @return
     */
    int update(AddressPO record);
}