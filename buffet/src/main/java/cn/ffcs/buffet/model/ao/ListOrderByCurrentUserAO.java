package cn.ffcs.buffet.model.ao;

import cn.ffcs.buffet.common.dto.Page;
import cn.ffcs.buffet.model.po.OrderPO;
import io.swagger.annotations.ApiModel;

import java.io.Serializable;

/**
 * @Description: 查询用户当前订单AO
 * @Author:
 * @Date: 2020/9/1 20:02
 */
@ApiModel(value = "/api/order/listOrderByCurrentUser")
public class ListOrderByCurrentUserAO implements Serializable {

    /**
     * 分页数据
     */
    private Page<OrderPO> page;

    /**
     * 订单状态
     */
    private String orderStatus;

    public ListOrderByCurrentUserAO() {
    }

    public ListOrderByCurrentUserAO(Page<OrderPO> page, String orderStatus) {
        this.page = page;
        this.orderStatus = orderStatus;
    }

    @Override
    public String toString() {
        return "ListOrderByCurrentUserAO{" +
                "page=" + page +
                ", orderStatus='" + orderStatus + '\'' +
                '}';
    }

    public Page<OrderPO> getPage() {
        return page;
    }

    public void setPage(Page<OrderPO> page) {
        this.page = page;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}
