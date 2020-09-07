package cn.ffcs.buffet.model.ao;

import io.swagger.annotations.ApiModel;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * @Description: 批量修改订单状态AO
 * @Author:
 * @Date: 2020/9/1 20:02
 */
@ApiModel(value = "/api/order/updateOrderByIdList")
public class UpdateOrderStatusAO implements Serializable  {

    /**
     * 订单idList
     */
    private Long[] idList;

    /**
     * 订单状态
     */
    private String orderStatus;

    public UpdateOrderStatusAO() {
    }

    public UpdateOrderStatusAO(Long[] idList, String orderStatus) {
        this.idList = idList;
        this.orderStatus = orderStatus;
    }

    @Override
    public String toString() {
        return "UpdateOrderStatusAO{" +
                "idList=" + Arrays.toString(idList) +
                ", orderStatus='" + orderStatus + '\'' +
                '}';
    }

    public Long[] getIdList() {
        return idList;
    }

    public void setIdList(Long[] idList) {
        this.idList = idList;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}
