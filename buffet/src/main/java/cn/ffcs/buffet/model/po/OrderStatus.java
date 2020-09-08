package cn.ffcs.buffet.model.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;

import java.util.Date;

@ApiModel
public class OrderStatus {

    /**
     * 订单状态记录id
     */
    private Integer id;

    /**
     *订单id
     */
    private Long orderId;

    /**
     * 订单状态
     */
    private String status;

    /**
     * 状态记录时间
     */
    private Date operateTime;

    /**
     * 创建时间
     */
    @JsonFormat(shape= JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    /**
     * 修改时间
     */
    @JsonFormat(shape= JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date changeTime;

    /**
     * 创建人
     */
    private Integer creator;

    /**
     * 修改人
     */
    private Integer modifier;

    public OrderStatus() {
    }

    public OrderStatus(Integer id, Long orderId, String status, Date operateTime, Date createTime, Date changeTime, Integer creator, Integer modifier) {
        this.id = id;
        this.orderId = orderId;
        this.status = status;
        this.operateTime = operateTime;
        this.createTime = createTime;
        this.changeTime = changeTime;
        this.creator = creator;
        this.modifier = modifier;
    }

    @Override
    public String toString() {
        return "OrderStatus{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", status='" + status + '\'' +
                ", operateTime=" + operateTime +
                ", createTime=" + createTime +
                ", changeTime=" + changeTime +
                ", creator=" + creator +
                ", modifier=" + modifier +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
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
