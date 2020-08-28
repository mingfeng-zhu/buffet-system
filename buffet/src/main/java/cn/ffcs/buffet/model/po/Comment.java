package cn.ffcs.buffet.model.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@ApiModel
public class Comment implements Serializable {

    /**
     * 评论id
     */
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 商品id
     */
    private Integer goodId;

    /**
     * 详单id
     */
    private Integer orderDetailId;

    /**
     * 订单id
     */
    private Long orderId;

    /**
     * 评论图片
     */
    private String image;

    /**
     * 评论星级
      */
    private BigDecimal commentStar;

    /**
     * 评语
     */
    private String commentDescription;

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

    private UserPO userPO;

    public Comment() {
    }

    public Comment(Integer id, Integer userId, Integer goodId, Integer orderDetailId, Long orderId, String image, BigDecimal commentStar, String commentDescription, Date createTime, Date changeTime, Integer creator, Integer modifier) {
        this.id = id;
        this.userId = userId;
        this.goodId = goodId;
        this.orderDetailId = orderDetailId;
        this.orderId = orderId;
        this.image = image;
        this.commentStar = commentStar;
        this.commentDescription = commentDescription;
        this.createTime = createTime;
        this.changeTime = changeTime;
        this.creator = creator;
        this.modifier = modifier;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", userId=" + userId +
                ", goodId=" + goodId +
                ", orderDetailId=" + orderDetailId +
                ", orderId=" + orderId +
                ", image='" + image + '\'' +
                ", commentStar=" + commentStar +
                ", commentDescription='" + commentDescription + '\'' +
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

    public Integer getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(Integer orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public BigDecimal getCommentStar() {
        return commentStar;
    }

    public void setCommentStar(BigDecimal commentStar) {
        this.commentStar = commentStar;
    }

    public String getCommentDescription() {
        return commentDescription;
    }

    public void setCommentDescription(String commentDescription) {
        this.commentDescription = commentDescription;
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

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public UserPO getUserPO() {
        return userPO;
    }

    public void setUserPO(UserPO userPO) {
        this.userPO = userPO;
    }
}