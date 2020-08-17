package cn.ffcs.buffet.model.dto;

import cn.ffcs.buffet.model.po.OrderDetail;
import cn.ffcs.buffet.model.po.OrderPO;
import io.swagger.annotations.ApiModel;

import java.math.BigDecimal;
import java.util.List;

@ApiModel
public class OrderDetailDTO {

    private OrderPO orderPO;

    private List<OrderDetail> orderDetailList;

    public OrderDetailDTO() {
    }

    public OrderDetailDTO(OrderPO orderPO, List<OrderDetail> orderDetailList) {
        this.orderPO = orderPO;
        this.orderDetailList = orderDetailList;
    }

    @Override
    public String toString() {
        return "OrderDetailDTO{" +
                "orderPO=" + orderPO +
                ", orderDetailList=" + orderDetailList +
                '}';
    }

    public OrderPO getOrderPO() {
        return orderPO;
    }

    public void setOrderPO(OrderPO orderPO) {
        this.orderPO = orderPO;
    }

    public List<OrderDetail> getOrderDetailList() {
        return orderDetailList;
    }

    public void setOrderDetailList(List<OrderDetail> orderDetailList) {
        this.orderDetailList = orderDetailList;
    }
}
