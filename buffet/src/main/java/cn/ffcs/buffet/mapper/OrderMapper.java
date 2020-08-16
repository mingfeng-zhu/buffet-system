package cn.ffcs.buffet.mapper;

import cn.ffcs.buffet.model.po.OrderPO;
import io.swagger.models.auth.In;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public interface OrderMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OrderPO record);

    int insertSelective(OrderPO record);

    OrderPO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderPO record);

    int updateByPrimaryKey(OrderPO record);

    /**
     * 查询某个用户的所有订单
     * @param userId 用户id
     * @param orderStatus 订单状态
     * @param orderId 订单流水号
     * @return
     */
    List<OrderPO> listOrder(Integer userId, String orderStatus, String orderId);

    /**
     * 删除指定订单id的订单
     * @param list 订单idlist
     */
    void deleteOrderByOrderId(List<Integer> list);

    /**
     * 修改订单状态，批量形式
     * @param list 订单idList
     * @param orderStatus 订单状态
     */
    void updateOrderByIdList(List<Integer> list, String orderStatus);

    /**
     * 获取交易订单总数
     * @return
     */
    int getTotalNumber();

    /**
     * 获取订单总交易额
     * @return
     */
    BigDecimal getTotalMoney();

    /**
     * 修改订单状态，app端使用
     * @param id 订单id
     * @param orderStatus 订单状态
     * @return
     */
    Integer editOrderStatus(Long id, String orderStatus);
}