package cn.ffcs.buffet.service.impl;

import cn.ffcs.buffet.mapper.OrderStatusMapper;
import cn.ffcs.buffet.model.po.OrderStatus;
import cn.ffcs.buffet.service.OrderStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderStatusServiceImpl implements OrderStatusService {

    @Autowired(required=false)
    private OrderStatusMapper orderStatusMapper;

    @Override
    public Integer insertOrderStatus(OrderStatus orderStatus) {
        return orderStatusMapper.insertSelective(orderStatus);
    }
}
