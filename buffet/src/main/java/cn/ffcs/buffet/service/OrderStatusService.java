package cn.ffcs.buffet.service;

import cn.ffcs.buffet.model.po.OrderStatus;

/**
 * @author huang.zhao
 * @date: 2020/08/05
 **/
public interface OrderStatusService {

    /**
     * 插入一条订单状态改变记录
     * @param orderStatus 订单状态记录
     * @return
     */
    Integer insertOrderStatus(OrderStatus orderStatus);

}
