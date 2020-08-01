package cn.ffcs.buffet.model.po;

import io.swagger.annotations.ApiModel;
import org.omg.CORBA.INTERNAL;

import java.io.Serializable;

/**
 * @Description 商品类
 * @Author chenzhenchao@ffcs.cn
 * @Date 2020/7/29 08:56
 */
@ApiModel
public class ProductPO extends BasePO implements Serializable {

    /**
     * 商品id
     */
    private Integer productId;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 商品描述
     */
    private String productDesc;

    /**
     * 商品分类id
     */
    private Integer productCategoryId;

    /**
     * 商品图片路径
     */
    private String productPicturePath;

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
}