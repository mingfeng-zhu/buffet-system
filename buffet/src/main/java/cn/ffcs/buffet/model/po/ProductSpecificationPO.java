package cn.ffcs.buffet.model.po;

import io.swagger.annotations.ApiModel;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Description 商品规格类
 * @Author chenzhenchao@ffcs.cn
 * @Date 2020/7/29 08:56
 */
@ApiModel
public class ProductSpecificationPO extends BasePO implements Serializable {

    /**
     * 商品规格id
     */
    private Integer productSpecificationId;

    /**
     * 商品规格
     */
    private String productSpecification;

    /**
     * 商品id
     */
    private Integer productId;

    /**
     * 商品库存量
     */
    private Integer productStorage;

    /**
     * 商品价格
     */
    private BigDecimal productPrice;

    /**
     * 商品规格状态
     */
    private Integer status;

    /**
     * 商品规格图片路径
     */
    private String productSpecificationPicture;

    public Integer getProductSpecificationId() {
        return productSpecificationId;
    }

    public void setProductSpecificationId(Integer productSpecificationId) {
        this.productSpecificationId = productSpecificationId;
    }

    public String getProductSpecification() {
        return productSpecification;
    }

    public void setProductSpecification(String productSpecification) {
        this.productSpecification = productSpecification;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getProductStorage() {
        return productStorage;
    }

    public void setProductStorage(Integer productStorage) {
        this.productStorage = productStorage;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getProductSpecificationPicture() {
        return productSpecificationPicture;
    }

    public void setProductSpecificationPicture(String productSpecificationPicture) {
        this.productSpecificationPicture = productSpecificationPicture;
    }
}