package cn.ffcs.buffet.model.dto;

import cn.ffcs.buffet.model.po.ShopCart;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;

import java.util.Date;

/**
 * @Description ShopCartDetailDTO
 * @Author huang.zhao
 * @Date 2020/08/15 23:33
 */
@ApiModel
public class ShopCartDetailDTO {

    /**
     * 购物车信息
     */
    private ShopCart shopCart;

    /**
     * 商品规格信息
     */
    private ProductSpecificationDTO productSpecificationDTO;

    public ShopCartDetailDTO() {
    }

    public ShopCartDetailDTO(ShopCart shopCart, ProductSpecificationDTO productSpecificationDTO) {
        this.shopCart = shopCart;
        this.productSpecificationDTO = productSpecificationDTO;
    }

    @Override
    public String toString() {
        return "ShopCartDetailDTO{" +
                "shopCart=" + shopCart +
                ", productSpecificationDTO=" + productSpecificationDTO +
                '}';
    }

    public ShopCart getShopCart() {
        return shopCart;
    }

    public void setShopCart(ShopCart shopCart) {
        this.shopCart = shopCart;
    }

    public ProductSpecificationDTO getProductSpecificationDTO() {
        return productSpecificationDTO;
    }

    public void setProductSpecificationDTO(ProductSpecificationDTO productSpecificationDTO) {
        this.productSpecificationDTO = productSpecificationDTO;
    }
}
