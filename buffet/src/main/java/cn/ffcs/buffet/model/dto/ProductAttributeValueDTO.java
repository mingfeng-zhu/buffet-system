package cn.ffcs.buffet.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * @Description ProductAttributeValueDTO
 * @Author chenzhenchao@ffcs.cn
 * @Date 2020/8/1 13:19
 */
@ApiModel
public class ProductAttributeValueDTO {

    @ApiModelProperty(value = "商品属性值ID", name = "productAttributeValueId", example = "1")
    private Integer productAttributeValueId;

    @ApiModelProperty(value = "商品属性值", name = "productAttributeName", required = true, example = "常温")
    @NotEmpty(message = "商品属性名称不能为null且不为空")
    @Size(min = 1, max = 50, message = "商品名称长度需在1至50个字符")
    private String productAttributeValue;

    @ApiModelProperty(value = "商品属性ID", name = "productAttributeId", example = "1")
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
