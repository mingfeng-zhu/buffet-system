package cn.ffcs.buffet.service;

import cn.ffcs.buffet.common.dto.Page;
import cn.ffcs.buffet.model.dto.ProductAttributeDTO;
import cn.ffcs.buffet.model.dto.ProductCategoryDTO;
import cn.ffcs.buffet.model.dto.ProductDTO;
import cn.ffcs.buffet.model.dto.ProductSpecificationDTO;
import cn.ffcs.buffet.model.po.ProductPO;
import com.github.pagehelper.PageInfo;

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
     * @param page
     * @param productCategoryId
     * @return
     */
    PageInfo<ProductDTO> selectProductListByProductCategoryId(Page<ProductDTO> page, Integer productCategoryId);

    /**
     * 根据商品名称查询商品
     * @param productName
     * @return
     */
    PageInfo<ProductDTO> selectProductListByProductName(Page<ProductDTO> page, String productName);

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
     * 获取全部商品
     * @param page
     * @return
     */
    PageInfo<ProductDTO> selectAllProductList(Page<ProductDTO> page);

    /**
     * 根据商品规格id集合返回商品规格集合
     * @param productSpecificationIdList
     * @return
     */
    List<ProductSpecificationDTO> selectSpecificationByProductSpecificationIdList(List<Integer> productSpecificationIdList);

    /**
     * 修改商品储存量, 修改库存量（订单支付时，订单取消时）
     * @param productSpecificationIdList
     * @param numberList
     * @return
     */
    Integer updateProductStorage(List<Integer> productSpecificationIdList, List<Integer> numberList);

    /**
     * 根据商品id获取商品详细信息以及商品评价
     * @param productId
     * @return
     */
    List<ProductDTO> selectProductDetailAndCommentList(Integer productId);
}
