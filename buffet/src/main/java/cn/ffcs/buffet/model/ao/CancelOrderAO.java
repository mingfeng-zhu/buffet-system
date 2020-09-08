package cn.ffcs.buffet.model.ao;

import io.swagger.annotations.ApiModel;

import java.io.Serializable;

/**
 * @Description: 取消订单AO
 * @Author:
 * @Date: 2020/9/7 20:02
 */
@ApiModel(value = "/api/order/cancelOrder")
public class CancelOrderAO implements Serializable {

    //订单id
    private Long id;

    public CancelOrderAO() {
    }

    public CancelOrderAO(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "CancelOrderAO{" +
                "id=" + id +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
