package cn.ffcs.buffet.model.dto;

import cn.ffcs.buffet.model.po.ProductPO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;

import javax.validation.constraints.Size;
import java.math.BigDecimal;

/**
 * @Description ProductSpecificationDTO
 * @Author chenzhenchao@ffcs.cn
 * @Date 2020/7/30 16:00
 */
@ApiModel
public class ProductSpecificationDTO {

    @ApiModelProperty(value = "商品规格ID", name = "productSpecificationId", example = "1")
    private Integer productSpecificationId;

    @ApiModelProperty(value = "商品规格", name = "productSpecification", example = "{\"attribute1\":\"value1\",\"attribute2\":\"value3\",\"attribute3\":\"value5\"}")
    @Size(min = 1, max = 255, message = "商品描述名长度需在1至255个字符")
    private String productSpecification;

    @ApiModelProperty(value = "商品ID", name = "productId", example = "1")
    private Integer productId;

    @ApiModelProperty(value = "商品库存", name = "productStorage", example = "100")
    private Integer productStorage;

    @ApiModelProperty(value = "商品该规格的价格", name = "productPrice", example = "99.99")
    private BigDecimal productPrice;

    @ApiModelProperty(value = "商品状态", name = "status", example = "0")
    private Integer status;

    @ApiModelProperty(value = "商品规格图片的路径", name = "productSpecificationPicture", example = "http://127.0.0.1:8082/productPicture/aisdfjaskldfjklasdfjlaskdjf.jpg")
    private String productSpecificationPicture;

    private ProductPO productPO;

    private Integer numberOfCart;

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
    public ProductPO getProductPO() {
        return productPO;
    }

    public void setProductPO(ProductPO productPO) {
        this.productPO = productPO;
    }

    public Integer getNumberOfCart() {
        return numberOfCart;
    }

    public void setNumberOfCart(Integer numberOfCart) {
        this.numberOfCart = numberOfCart;
    }

    @Override
    public String toString() {
        return "ProductSpecificationDTO{" +
                "productSpecificationId=" + productSpecificationId +
                ", productSpecification='" + productSpecification + '\'' +
                ", productId=" + productId +
                ", productStorage=" + productStorage +
                ", productPrice=" + productPrice +
                ", status=" + status +
                ", productSpecificationPicture='" + productSpecificationPicture + '\'' +
                ", productPO=" + productPO +
                ", numberOfCart=" + numberOfCart +
                '}';
    }
}
