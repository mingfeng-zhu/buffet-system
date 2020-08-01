package cn.ffcs.buffet.model.po;

import io.swagger.annotations.ApiModel;

import java.io.Serializable;

/**
 * @Description 商品属性值类
 * @Author chenzhenchao@ffcs.cn
 * @Date 2020/7/29 08:56
 */
@ApiModel
public class ProductAttributeValuePO extends BasePO implements Serializable {

    /**
     * 商品属性值id
     */
    private Integer productAttributeValueId;

    /**
     * 商品属性值
     */
    private String productAttributeValue;

    /**
     * 商品属性id
     */
    private Integer productAttributeId;

    public Integer getProductAttributeValueId() {
        return productAttributeValueId;
    }

    public void setProductAttributeValueId(Integer productAttributeValueId) {
        this.productAttributeValueId = productAttributeValueId;
    }

    public String getProductAttributeValue() {
        return productAttributeValue;
    }

    public void setProductAttributeValue(String productAttributeValue) {
        this.productAttributeValue = productAttributeValue;
    }

    public Integer getProductAttributeId() {
        return productAttributeId;
    }

    public void setProductAttributeId(Integer productAttributeId) {
        this.productAttributeId = productAttributeId;
    }
}