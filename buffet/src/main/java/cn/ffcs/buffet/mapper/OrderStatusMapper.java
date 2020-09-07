package cn.ffcs.buffet.mapper;

import cn.ffcs.buffet.model.po.OrderStatus;

import java.util.List;

public interface OrderStatusMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderStatus record);

    int insertSelective(OrderStatus record);

    OrderStatus selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderStatus record);

    int updateByPrimaryKey(OrderStatus record);

    List<OrderStatus> listOrderStatusRecordByOrderId(Long orderId);

    Integer insertOrderStatusList(List<OrderStatus> list);
}
