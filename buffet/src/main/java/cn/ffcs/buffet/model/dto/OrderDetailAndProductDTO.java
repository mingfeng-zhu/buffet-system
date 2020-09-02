package cn.ffcs.buffet.model.dto;

import cn.ffcs.buffet.model.po.OrderDetail;
import cn.ffcs.buffet.model.po.OrderPO;
import io.swagger.annotations.ApiModel;

import java.util.List;

@ApiModel
public class OrderDetailAndProductDTO {

    private OrderDetail orderDetail;

    private ProductSpecificationDTO productSpecificationDTO;

    public OrderDetailAndProductDTO() {
    }

    public OrderDetailAndProductDTO(OrderDetail orderDetail, ProductSpecificationDTO productSpecificationDTO) {
        this.orderDetail = orderDetail;
        this.productSpecificationDTO = productSpecificationDTO;
    }

    @Override
    public String toString() {
        return "OrderDetailAndProductDTO{" +
                "orderDetail=" + orderDetail +
                ", productSpecificationDTO=" + productSpecificationDTO +
                '}';
    }

    public OrderDetail getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(OrderDetail orderDetail) {
        this.orderDetail = orderDetail;
    }

    public ProductSpecificationDTO getProductSpecificationDTO() {
        return productSpecificationDTO;
    }

    public void setProductSpecificationDTO(ProductSpecificationDTO productSpecificationDTO) {
        this.productSpecificationDTO = productSpecificationDTO;
    }
}
