package cn.ffcs.buffet.model.dto;

import cn.ffcs.buffet.model.po.OrderDetail;
import cn.ffcs.buffet.model.po.OrderPO;
import io.swagger.annotations.ApiModel;

import java.math.BigDecimal;
import java.util.List;

@ApiModel
public class OrderDetailDTO {

    private OrderPO orderPO;

    private List<OrderDetailAndProductDTO> orderDetailAndProductDTO;

    public OrderDetailDTO() {
    }

    public OrderDetailDTO(OrderPO orderPO, List<OrderDetailAndProductDTO> orderDetailAndProductDTO) {
        this.orderPO = orderPO;
        this.orderDetailAndProductDTO = orderDetailAndProductDTO;
    }

    @Override
    public String toString() {
        return "OrderDetailDTO{" +
                "orderPO=" + orderPO +
                ", orderDetailAndProductDTO=" + orderDetailAndProductDTO +
                '}';
    }

    public OrderPO getOrderPO() {
        return orderPO;
    }

    public void setOrderPO(OrderPO orderPO) {
        this.orderPO = orderPO;
    }

    public List<OrderDetailAndProductDTO> getOrderDetailAndProductDTO() {
        return orderDetailAndProductDTO;
    }

    public void setOrderDetailAndProductDTO(List<OrderDetailAndProductDTO> orderDetailAndProductDTO) {
        this.orderDetailAndProductDTO = orderDetailAndProductDTO;
    }
}
