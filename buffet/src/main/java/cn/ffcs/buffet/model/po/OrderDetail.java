package cn.ffcs.buffet.model.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 详单实体类
 * @author: huang.zhao
 * @date: 2020/08/03
 **/
@ApiModel
public class OrderDetail implements Serializable {

    /**
     * 详单id
     */
    private Integer id;

    /**
     * 所属订单id
     */
    private Long orderId;

    /**
     * 商品规格id
     */
    private Integer goodId;

    /**
     * 商品数量
     */
    private Integer goodCount;

    /**
     * 评论
     */
    private String comment;

    /**
     * 价格
     */
    private BigDecimal money;

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
     * 创建者
     */
    private Integer creator;

    /**
     * 修改者
     */
    private Integer modifier;

    public OrderDetail() {
    }

    public OrderDetail(Integer id, Long orderId, Integer goodId, Integer goodCount, String comment, BigDecimal money, Date createTime, Date changeTime, Integer creator, Integer modifier) {
        this.id = id;
        this.orderId = orderId;
        this.goodId = goodId;
        this.goodCount = goodCount;
        this.comment = comment;
        this.money = money;
        this.createTime = createTime;
        this.changeTime = changeTime;
        this.creator = creator;
        this.modifier = modifier;
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", goodId=" + goodId +
                ", goodCount=" + goodCount +
                ", comment='" + comment + '\'' +
                ", money=" + money +
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

    public Integer getGoodId() {
        return goodId;
    }

    public void setGoodId(Integer goodId) {
        this.goodId = goodId;
    }

    public Integer getGoodCount() {
        return goodCount;
    }

    public void setGoodCount(Integer goodCount) {
        this.goodCount = goodCount;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
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