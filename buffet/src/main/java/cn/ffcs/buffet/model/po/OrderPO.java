package cn.ffcs.buffet.model.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单实体类
 * @author: huang.zhao
 * @date: 2019/09/11
 **/
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel
public class OrderPO implements Serializable {

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
     * 0:店内用餐订单 1：外派订单
     */
    private String orderType;

    /**
     * 订单状态: 0-未支付 1-未配送 2-配送中 3-已到达 4-已取消
     */
    @ApiModelProperty(value = "订单状态：0为已取消、1为待支付、2为待接单、3为制作中、4为派送中、5为已完成、6为取消中", name = "orderStatus")
    private String orderStatus;

    /**
     * 状态： 0：已删除  1：未删除
     */
    private String status;

    /**
     * 订单创建时间
     */
    @JsonFormat(shape= JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    /**
     * 订单修改时间
     */
    @JsonFormat(shape= JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date changeTime;

    /**
     * 创建者
     */
    private Integer creator;

    /**
     * 修改者
     */
    private Integer modifier;

    public OrderPO() {
    }

    public OrderPO(Long id, String orderId, Integer userId, Integer addressId, BigDecimal totalMoney, String orderType, String orderStatus, String status, Date createTime, Date changeTime, Integer creator, Integer modifier) {
        this.id = id;
        this.orderId = orderId;
        this.userId = userId;
        this.addressId = addressId;
        this.totalMoney = totalMoney;
        this.orderType = orderType;
        this.orderStatus = orderStatus;
        this.status = status;
        this.createTime = createTime;
        this.changeTime = changeTime;
        this.creator = creator;
        this.modifier = modifier;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderId='" + orderId + '\'' +
                ", userId=" + userId +
                ", addressId=" + addressId +
                ", totalMoney=" + totalMoney +
                ", orderType='" + orderType + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", status='" + status + '\'' +
                ", createTime=" + createTime +
                ", changeTime=" + changeTime +
                ", creator=" + creator +
                ", modifier=" + modifier +
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

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getChangeTime() {
        return changeTime;
    }

    public void setChangeTime(Date changeTime) {
        this.changeTime = changeTime;
    }

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    public Integer getModifier() {
        return modifier;
    }

    public void setModifier(Integer modifier) {
        this.modifier = modifier;
    }
}
