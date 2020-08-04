package cn.ffcs.buffet.model.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;

import java.util.Date;

@ApiModel
public class ShopCart {

    /**
     * 购物车记录id
     */
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 商品规格id
     */
    private Integer goodId;

    /**
     * 支付状态（0:未支付 1:已支付）
     */
    private String payStatus;

    /**
     * 商品数量
     */
    private Integer goodCount;

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

    public ShopCart() {
    }

    public ShopCart(Integer id, Integer userId, Integer goodId, String payStatus, Integer goodCount, Date createTime, Date changeTime, Integer creator, Integer modifier) {
        this.id = id;
        this.userId = userId;
        this.goodId = goodId;
        this.payStatus = payStatus;
        this.goodCount = goodCount;
        this.createTime = createTime;
        this.changeTime = changeTime;
        this.creator = creator;
        this.modifier = modifier;
    }

    @Override
    public String toString() {
        return "ShopCart{" +
                "id=" + id +
                ", userId=" + userId +
                ", goodId=" + goodId +
                ", payStatus='" + payStatus + '\'' +
                ", goodCount=" + goodCount +
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getGoodId() {
        return goodId;
    }

    public void setGoodId(Integer goodId) {
        this.goodId = goodId;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public Integer getGoodCount() {
        return goodCount;
    }

    public void setGoodCount(Integer goodCount) {
        this.goodCount = goodCount;
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