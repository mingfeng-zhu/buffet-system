package cn.ffcs.buffet.service.impl;

import cn.ffcs.buffet.common.dto.Result;
import cn.ffcs.buffet.mapper.OrderStatusMapper;
import cn.ffcs.buffet.model.po.OrderStatus;
import cn.ffcs.buffet.service.OrderStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderStatusServiceImpl implements OrderStatusService {

    @Autowired(required=false)
    private OrderStatusMapper orderStatusMapper;

    @Override
    public Integer insertOrderStatus(OrderStatus orderStatus) {
        return orderStatusMapper.insertSelective(orderStatus);
    }

    @Override
    public Result listOrderStatusRecordByOrderId(Long orderId) {
        return Result.success(orderStatusMapper.listOrderStatusRecordByOrderId(orderId));
    }

    @Override
    public Integer insertOrderStatusList(List<OrderStatus> orderStatusList) {
        return orderStatusMapper.insertOrderStatusList(orderStatusList);
    }
}
