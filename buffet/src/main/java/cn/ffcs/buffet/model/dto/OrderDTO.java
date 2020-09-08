package cn.ffcs.buffet.model.dto;

import cn.ffcs.buffet.model.po.AddressPO;
import cn.ffcs.buffet.model.po.UserAddress;
import cn.ffcs.buffet.model.po.UserPO;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;

@ApiModel
public class OrderDTO {

    /**
     * 订单id
     */
    private Long id;

    /**
     * 订单流水号
     */
    private String orderId;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 收货地址id
     */
    private Integer addressId;

    /**
     * 总金额
     */
    private BigDecimal totalMoney;

    /**
     * 订单状态: 0-未支付 1-未配送 2-配送中 3-已到达 4-已取消
     */
    @ApiModelProperty(value = "订单状态：0为已取消、1为待支付、2为待接单、3为制作中、4为派送中、5为已完成、6为取消中", name = "orderStatus")
    private String orderStatus;

    /**
     * 订单创建时间
     */
    @JsonFormat(shape= JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    /**
     * 用户信息
     */
    private UserPO userPO;

    /**
     * 地址信息
     */
    private AddressPO addressPO;

    public OrderDTO() {
    }

    public OrderDTO(Long id, String orderId, Integer userId, Integer addressId, BigDecimal totalMoney, String orderStatus
            , Date createTime, UserPO userPO, AddressPO addressPO) {
        this.id = id;
        this.orderId = orderId;
        this.userId = userId;
        this.addressId = addressId;
        this.totalMoney = totalMoney;
        this.orderStatus = orderStatus;
        this.createTime = createTime;
        this.userPO = userPO;
        this.addressPO = addressPO;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "id=" + id +
                ", orderId='" + orderId + '\'' +
                ", userId=" + userId +
                ", addressId=" + addressId +
                ", totalMoney=" + totalMoney +
                ", orderStatus='" + orderStatus + '\'' +
                ", createTime=" + createTime +
                ", userPO=" + userPO +
                ", addressPO=" + addressPO +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public UserPO getUserPO() {
        return userPO;
    }

    public void setUserPO(UserPO userPO) {
        this.userPO = userPO;
    }

    public AddressPO getAddressPO() {
        return addressPO;
    }

    public void setAddressPO(AddressPO addressPO) {
        this.addressPO = addressPO;
    }
}
