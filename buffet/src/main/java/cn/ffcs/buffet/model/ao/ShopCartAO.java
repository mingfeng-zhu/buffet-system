package cn.ffcs.buffet.model.ao;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @Description: 购物车添加AO
 * @Author:
 * @Date: 2020/9/1 20:02
 */
@ApiModel(value = "/api/shopCart/addShopCartRecord参数")
public class ShopCartAO implements Serializable {

    /**
     * 商品规格id
     */
    @ApiModelProperty(value = "商品规格id", name = "productSpecificationId", required = true, example = "9")
    private Integer productSpecificationId;

    /**
     * 商品数量
     */
    @ApiModelProperty(value = "商品数量", name = "goodCount", required = true, example = "1")
    private Integer goodCount;

    public ShopCartAO() {
    }

    public ShopCartAO(Integer productSpecificationId, Integer goodCount) {
        this.productSpecificationId = productSpecificationId;
        this.goodCount = goodCount;
    }

    @Override
    public String toString() {
        return "shopCartAO{" +
                "productSpecificationId=" + productSpecificationId +
                ", goodCount=" + goodCount +
                '}';
    }

    public Integer getProductSpecificationId() {
        return productSpecificationId;
    }

    public void setProductSpecificationId(Integer productSpecificationId) {
        this.productSpecificationId = productSpecificationId;
    }

    public Integer getGoodCount() {
        return goodCount;
    }

    public void setGoodCount(Integer goodCount) {
        this.goodCount = goodCount;
    }
}
