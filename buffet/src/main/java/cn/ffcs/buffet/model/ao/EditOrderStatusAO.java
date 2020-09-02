package cn.ffcs.buffet.model.ao;

import io.swagger.annotations.ApiModel;

import java.io.Serializable;

/**
 * @Description: 生成订单AO
 * @Author:
 * @Date: 2020/9/1 20:02
 */
@ApiModel(value = "/api/order/editOrderStatusAO")
public class EditOrderStatusAO implements Serializable {

    /**
     * 订单id
     */
    private Long id;

    /**
     * 订单状态
     */
    private String orderStatus;

    public EditOrderStatusAO() {
    }

    public EditOrderStatusAO(Long id, String orderStatus) {
        this.id = id;
        this.orderStatus = orderStatus;
    }

    @Override
    public String toString() {
        return "EditOrderStatusAO{" +
                "id=" + id +
                ", orderStatus='" + orderStatus + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}
