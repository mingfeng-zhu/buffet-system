package cn.ffcs.buffet.service;

import cn.ffcs.buffet.model.dto.ProductAttributeDTO;
import cn.ffcs.buffet.model.dto.ProductCategoryDTO;
import cn.ffcs.buffet.model.dto.ProductDTO;
import cn.ffcs.buffet.model.dto.ProductSpecificationDTO;

import java.util.List;

/**
 * @Description ProductModuleService
 * @Author chenzhenchao@ffcs.cn
 * @Date 2020/7/29 15:41
 */
public interface ProductModuleService {

    /**
     * 查询所有商品分类,及分类中的商品
     * @return
     */
    List<ProductCategoryDTO> selectProductCategoryList();

    /**
     * 根据商品分类id获取商品列表
     * @param productCategoryId
     * @return
     */
    List<ProductDTO> selectProductListByProductCategoryId(Integer productCategoryId);

    /**
     * 根据商品名称查询商品
     * @param productName
     * @return
     */
    List<ProductDTO> selectProductListByProductName(String productName);

    /**
     * 根据商品id获取商品属性列表
     * @param productId
     * @return
     */
    List<ProductAttributeDTO> selectProductAttributeByProductId(Integer productId);

    /**
     * 根据商品id和商品规格获取该商品规格的详细信息
     * @param productId
     * @param productSpecification
     * @return
     */
    ProductSpecificationDTO selectSpecificationByProductIdAndSpecification(Integer productId, String productSpecification);

    /**
     * 根据商品规格id集合返回商品规格集合
     * @param productSpecificationIdList
     * @return
     */
    List<ProductSpecificationDTO> selectSpecificationByProductSpecificationIdList(List<Integer> productSpecificationIdList);

    /**
     * 修改商品储存量, 库存量 - 1（订单支付时）
     * @param productSpecificationIdList
     * @return
     */
    Integer updateProductStorageToMinusOne(List<Integer> productSpecificationIdList);
}
