package cn.ffcs.buffet.model.po;

import io.swagger.annotations.ApiModel;

import java.io.Serializable;

/**
 * @Description 商品属性类
 * @Author chenzhenchao@ffcs.cn
 * @Date 2020/7/29 08:56
 */
@ApiModel
public class ProductAttributePO extends BasePO implements Serializable {

    /**
     * 商品属性id
     */
    private Integer productAttributeId;

    /**
     * 商品属性名称
     */
    private String productAttributeName;

    /**
     * 商品id
     */
    private Integer productId;

    public Integer getProductAttributeId() {
        return productAttributeId;
    }

    public void setProductAttributeId(Integer productAttributeId) {
        this.productAttributeId = productAttributeId;
    }

    public String getProductAttributeName() {
        return productAttributeName;
    }

    public void setProductAttributeName(String productAttributeName) {
        this.productAttributeName = productAttributeName;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }
}