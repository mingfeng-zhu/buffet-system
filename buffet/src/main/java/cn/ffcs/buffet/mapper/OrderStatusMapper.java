package cn.ffcs.buffet.mapper;

import cn.ffcs.buffet.model.po.OrderStatus;

public interface OrderStatusMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderStatus record);

    int insertSelective(OrderStatus record);

    OrderStatus selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderStatus record);

    int updateByPrimaryKey(OrderStatus record);
}