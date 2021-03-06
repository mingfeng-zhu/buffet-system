package cn.ffcs.buffet.service;

import cn.ffcs.buffet.common.dto.Page;
import cn.ffcs.buffet.common.dto.Result;
import cn.ffcs.buffet.model.dto.OrderDTO;
import cn.ffcs.buffet.model.po.OrderPO;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author huang.zhao
 * @date: 2020/08/05
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
    Result listOrder(Integer userId, String orderStatus, Page<OrderDTO> page, String orderId);

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
    Result updateOrderByIdList(List<Long> list, String orderStatus);

    /**
     * 查询订单总数、总交易额
     * @return
     */
    Result getTotalNumberAndMoney();

    /**
     * 生成订单，订单未待支付状态
     * @param idList
     * @param totalMoney
     * @param totalMoneyList
     * @param addressId
     * @param goodCountList
     * @return
     */
    Result addOrder(Integer[] idList, BigDecimal totalMoney, BigDecimal[] totalMoneyList, Integer addressId, Integer[] goodCountList, Integer[] shopCartIdList);

    /**
     * 支付订单
     * @param id 订单id
     * @param orderStatus 订单状态
     * @return
     */
    Result editOrderStatus(Long id, String orderStatus);

    /**
     * 取消订单
     * @param id 订单id
     * @return
     */
    Result cancelOrder(Long id);

    /**
     * 支付订单
     * @param id 支付订单
     * @param idList 商品idList
     * @param goodCountList 商品数量list
     * @return
     */
    Result payOrder(Long id, @RequestParam(required = false, value = "idList[]") Integer[] idList,
                    @RequestParam(required = false, value = "goodCountList[]") Integer[] goodCountList);

    /**
     * 获取当前用户的订单信息
     * @param userId 用户id
     * @param page 分页信息
     * @param orderStatus 订单状态
     * @return
     */
    Result listOrderByCurrentId(Integer userId, Page<OrderPO> page, String orderStatus);

    /**
     * 获取指定订单信息
     * @param id 订单id
     * @return
     */
    Result getOrderById(Long id);

    /**
     * 取消订单，用于后台管理员批量取消订单
     * @param idList 订单列表
     * @param orderStatus 订单状态
     * @return
     */
    Result cancelOrderList(List<Long> idList, String orderStatus);

    /**
     * 获取最近dayCount天的数据
     * @param dayCount 天数
     * @return
     */
    Result getEchartsData(Integer dayCount);
}
