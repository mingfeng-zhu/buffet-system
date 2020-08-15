package cn.ffcs.buffet.model.dto;

import cn.ffcs.buffet.model.po.Comment;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Description ProductDTO
 * @Author chenzhenchao@ffcs.cn
 * @Date 2020/7/30 11:48
 */
@ApiModel
public class ProductDTO {

    @ApiModelProperty(value = "商品ID", name = "productId", example = "1")
    private Integer productId;

    @ApiModelProperty(value = "商品名称", name = "productName", example = "米饭")
    @NotEmpty(message = "商品名称不能为null且不为空")
    @Size(min = 1, max = 50, message = "商品名称长度需在1至50个字符")
    private String productName;

    @ApiModelProperty(value = "商品描述", name = "productDesc")
    @Size(min = 1, max = 100, message = "商品描述名长度需在1至100个字符")
    private String productDesc;

    @ApiModelProperty(value = "商品类别ID", name = "productCategoryId", example = "1")
    private Integer productCategoryId;

    @ApiModelProperty(value = "商品图片路径", name = "productPicturePath", example = "http://127.0.0.1:8082/productPicture/aisdfjaskldfjklasdfjlaskdjf.jpg")
    @NotEmpty(message = "商品图片路径不能为null且不为空")
    private String productPicturePath;

    @ApiModelProperty(value = "商品最高价", name = "maxPrice", example = "999.99")
    private BigDecimal maxPrice;

    @ApiModelProperty(value = "商品最低价", name = "minPrice", example = "0.99")
    private BigDecimal minPrice;

    @ApiModelProperty(value = "商品库存", name = "productStorage", example = "100")
    private Integer productStorage;

    @ApiModelProperty(value = "商品销量", name = "salesVolume", example = "100")
    private Integer salesVolume;

    @ApiModelProperty(value = "商品规格数量", name = "specificationNumber", example = "8")
    private Integer specificationNumber;

    private List<ProductSpecificationDTO> productSpecificationDTOList;

    private List<Comment> commentList;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public Integer getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(Integer productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public String getProductPicturePath() {
        return productPicturePath;
    }

    public void setProductPicturePath(String productPicturePath) {
        this.productPicturePath = productPicturePath;
    }

    public BigDecimal getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(BigDecimal maxPrice) {
        this.maxPrice = maxPrice;
    }

    public BigDecimal getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(BigDecimal minPrice) {
        this.minPrice = minPrice;
    }

    public Integer getProductStorage() {
        return productStorage;
    }

    public void setProductStorage(Integer productStorage) {
        this.productStorage = productStorage;
    }

    public Integer getSalesVolume() {
        return salesVolume;
    }

    public void setSalesVolume(Integer salesVolume) {
        this.salesVolume = salesVolume;
    }

    public Integer getSpecificationNumber() {
        return specificationNumber;
    }

    public void setSpecificationNumber(Integer specificationNumber) {
        this.specificationNumber = specificationNumber;
    }


    public List<ProductSpecificationDTO> getProductSpecificationDTOList() {
        return productSpecificationDTOList;
    }

    public void setProductSpecificationDTOList(List<ProductSpecificationDTO> productSpecificationDTOList) {
        this.productSpecificationDTOList = productSpecificationDTOList;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    @Override
    public String toString() {
        return "ProductDTO{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", productDesc='" + productDesc + '\'' +
                ", productCategoryId=" + productCategoryId +
                ", productPicturePath='" + productPicturePath + '\'' +
                ", maxPrice=" + maxPrice +
                ", minPrice=" + minPrice +
                ", productStorage=" + productStorage +
                ", salesVolume=" + salesVolume +
                ", specificationNumber=" + specificationNumber +
                ", productSpecificationDTOList=" + productSpecificationDTOList +
                ", commentList=" + commentList +
                '}';
    }
}
