package cn.ffcs.buffet.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * @Description ProductCategoryDTO
 * @Author chenzhenchao@ffcs.cn
 * @Date 2020/7/29 15:55
 */
@ApiModel
public class ProductCategoryDTO{

    @ApiModelProperty(value = "商品类别ID", name = "productCategoryId", example = "1")
    private Integer productCategoryId;

    @ApiModelProperty(value = "商品类别名称", name = "categoryName", required = true, example = "主食")
    @NotEmpty(message = "商品类别名不能为null且不为空")
    @Size(min = 1, max = 50, message = "商品类别名长度需在1至50个字符")
    private String categoryName;

    @ApiModelProperty(value = "商品类别描述", name = "categoryName")
    @Size(min = 1, max = 100, message = "商品类别名长度需在1至100个字符")
    private String categoryDesc;

    private List<ProductDTO> productDTOList;

    public Integer getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(Integer productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryDesc() {
        return categoryDesc;
    }

    public void setCategoryDesc(String categoryDesc) {
        this.categoryDesc = categoryDesc;
    }

    public List<ProductDTO> getProductDTOList() {
        return productDTOList;
    }

    public void setProductDTOList(List<ProductDTO> productDTOList) {
        this.productDTOList = productDTOList;
    }

    @Override
    public String toString() {
        return "ProductCategoryDTO{" +
                "productCategoryId=" + productCategoryId +
                ", categoryName='" + categoryName + '\'' +
                ", categoryDesc='" + categoryDesc + '\'' +
                ", productDTOList=" + productDTOList +
                '}';
    }
}
