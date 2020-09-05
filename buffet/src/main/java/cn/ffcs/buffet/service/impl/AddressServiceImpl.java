package cn.ffcs.buffet.service.impl;


import cn.ffcs.buffet.common.util.TokenUtil;
import cn.ffcs.buffet.mapper.AddressPOMapper;
import cn.ffcs.buffet.mapper.UserPOMapper;
import cn.ffcs.buffet.model.po.AddressPO;
import cn.ffcs.buffet.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Description:地址服务类实现类
 * @Author: mingfeng.zhu
 * @Date: 2020/9/3 15:09
 */
@Service
public class AddressServiceImpl implements AddressService {
    /**
     * 地址mapper接口
     */
    @Autowired
    private AddressPOMapper addressPOMapper;
    /**
     * 用户mapper接口
     */
    @Autowired
    private UserPOMapper userPOMapper;

    @Override
    public AddressPO getAddressById(Integer id) {
        return addressPOMapper.getAddressById(id);
    }

    @Override
    public List<AddressPO> getAddressListByUserId(Integer userId) {
        //是用户请求的从token中获取username再获取userId
        if(null==userId){
            userId=TokenUtil.getUserIdAndUserTelOfToken().getUserId();
        }
        return addressPOMapper.getAddressListByUserId(userId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public int insert(AddressPO record) {
        record.setUserId(TokenUtil.getUserIdAndUserTelOfToken().getUserId());
        record.setCreateBy(TokenUtil.getUserIdAndUserTelOfToken().getUserId().toString());
        record.setUpdateBy(TokenUtil.getUserIdAndUserTelOfToken().getUserId().toString());
        return addressPOMapper.insert(record);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public int update(AddressPO record) {
        return addressPOMapper.update(record);
    }
}
