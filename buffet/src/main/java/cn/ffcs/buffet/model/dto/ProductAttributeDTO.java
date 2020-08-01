package cn.ffcs.buffet.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @Description ProductAttributeDTO
 * @Author chenzhenchao@ffcs.cn
 * @Date 2020/8/1 13:14
 */
@ApiModel
public class ProductAttributeDTO {

    @ApiModelProperty(value = "商品属性ID", name = "productAttributeId", example = "1")
    private Integer productAttributeId;

    @ApiModelProperty(value = "商品属性名称", name = "productAttributeName", required = true, example = "温度")
    @NotEmpty(message = "商品属性名称不能为null且不为空")
    @Size(min = 1, max = 50, message = "商品名称长度需在1至50个字符")
    private String productAttributeName;

    @ApiModelProperty(value = "商品ID", name = "productId", example = "1")
    private Integer productId;

    private List<ProductAttributeValueDTO> productAttributeValueDTOList;

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

    public List<ProductAttributeValueDTO> getProductAttributeValueDTOList() {
        return productAttributeValueDTOList;
    }

    public void setProductAttributeValueDTOList(List<ProductAttributeValueDTO> productAttributeValueDTOList) {
        this.productAttributeValueDTOList = productAttributeValueDTOList;
    }
}
