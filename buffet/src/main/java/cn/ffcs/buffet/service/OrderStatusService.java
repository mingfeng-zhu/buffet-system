package cn.ffcs.buffet.service;

import cn.ffcs.buffet.common.dto.Result;
import cn.ffcs.buffet.model.po.OrderStatus;

import java.util.List;

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

    /**
     * 获取指定订单的时间线
     * @param orderId 订单id
     * @return
     */
    Result listOrderStatusRecordByOrderId(Long orderId);

    /**
     * 批量插入订单修改记录
     * @param orderStatusList 订单修改记录列表
     */
    Integer insertOrderStatusList(List<OrderStatus> orderStatusList);
}
