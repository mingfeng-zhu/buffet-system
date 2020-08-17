package cn.ffcs.buffet.service;

import cn.ffcs.buffet.model.po.OrderDetail;

import java.util.List;

/**
 * @author huang.zhao
 * @date: 2020/08/04
 **/
public interface OrderDetailService {

    /**
     * 创建订单时，批量插入详单
     * @param list 详单数组
     * @return
     */
    Integer insertList(List<OrderDetail> list);

    /**
     * 批量查询订单idList下的详单信息
     * @param idList 订单idList
     * @return
     */
    List<OrderDetail> listOrderDetailByOrderIdList(List<Long> idList);
}
