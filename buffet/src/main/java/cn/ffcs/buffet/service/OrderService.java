package cn.ffcs.buffet.service;

import cn.ffcs.buffet.common.dto.Page;
import cn.ffcs.buffet.common.dto.Result;
import cn.ffcs.buffet.model.po.OrderPO;

import java.util.List;

/**
 * @author huang.zhao
 * @date: 2019/09/18
 **/
public interface OrderService {

    /**
     * 查询某个用户的所有订单
     * @param userId 用户id
     * @param orderStatus 订单状态
     * @param page 分页数据
     * @param orderId 订单流水号
     * @return
     */
    Result listOrder(Integer userId, String orderStatus, Page<OrderPO> page, String orderId);

    /**
     * 删除指定订单id的订单，批量形式
     * @param list 订单idList
     * @return
     */
    Result deleteOrderByOrderId(List<Integer> list);

    /**
     * 修改订单状态，批量形式
     * @param orderStatus 订单状态
     * @param list 订单idList
     * @return
     */
    Result updateOrderByIdList(List<Integer> list, String orderStatus);

    /**
     * 查询订单总数、总交易额
     * @return
     */
    Result getTotalNumberAndMoney();
}
