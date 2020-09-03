package cn.ffcs.buffet.service;


import cn.ffcs.buffet.model.po.AddressPO;

import java.util.List;

/**
 * @Description:
 * @Author: mingfeng.zhu
 * @Date: 2020/9/3 15:07
 */
public interface AddressService {
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
