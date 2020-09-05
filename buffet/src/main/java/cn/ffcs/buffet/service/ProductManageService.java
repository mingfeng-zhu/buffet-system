package cn.ffcs.buffet.service;

import cn.ffcs.buffet.common.dto.Page;
import cn.ffcs.buffet.model.dto.ProductAttrManagerDTO;
import cn.ffcs.buffet.model.dto.ProductAttributeDTO;
import cn.ffcs.buffet.model.dto.ProductManagerDTO;
import cn.ffcs.buffet.model.po.*;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @ClassName: ProductManageSerice
 * @Author: chenzc
 * @Date: 2020/8/22
 * @Description:
 */
public interface ProductManageService {

    /**
     * 分页获取商品分类
     * @param page
     * @param categoryName
     * @return
     */
    PageInfo<ProductCategoryPO> getProductCategoryList(Page page, String categoryName);

    /**
     * 根据分类id获取商品分类
     * @param productCategoryId
     * @return
     */
    ProductCategoryPO getProductCategoryByproductCategoryId(Integer productCategoryId);

    /**
     * 更新商品分类
     * @param categoryPO
     * @return
     */
    Integer updateProductCategory(ProductCategoryPO categoryPO);

    /**
     * 新增商品分类
     * @param categoryPO
     * @return
     */
    Integer addProductCategory(ProductCategoryPO categoryPO);

    /**
     * 删除商品分类
     * @param productCategoryId
     * @return
     */
    Integer deleteProductCategory(Integer productCategoryId);


    /**
     * 获取商品列表
     * @param page
     * @param productName
     * @return
     */
    PageInfo<ProductManagerDTO> getProductList(Page page, String productName);

    /**
     * 添加商品
     * @param productPO
     * @param productSpecificationPO
     * @return
     */
    ProductPO addProduct(ProductPO productPO, ProductSpecificationPO productSpecificationPO);

    /**
     * 修改商品
     * @param productPO
     * @return
     */
    int updateProduct(ProductPO productPO);

    /**
     * 删除商品
     * @param productId
     * @return
     */
    Integer deleteProduct(Integer productId);

    /**
     * 下架商品
     * @param productId
     * @return
     */
    Integer dropProduct(Integer productId);

    /**
     * 上架商品
     * @param productId
     * @return
     */
    Integer upProduct(Integer productId);

    /**
     * 根据商品id获取属性及其属性值
     * @param productId
     * @return
     */
    List<ProductAttrManagerDTO> selectProductAttributeByProductId(Integer productId);

    /**
     * 添加商品规格属性
     * @param productAttributePO
     * @return
     */
    Integer addProductAttribute(ProductAttributePO productAttributePO);

    /**
     * 修改商品规格属性
     * @param productAttributePO
     * @return
     */
    Integer updateProductAttribute(ProductAttributePO productAttributePO);

    /**
     * 删除规格属性
     * @param productAttributeId
     * @return
     */
    Integer deleteProductAttribute(Integer productAttributeId);

    /**
     * 删除规格属性值
     * @param productId
     * @param productAttributeId
     * @param productAttributeValueId
     * @return
     */
    Integer deleteAttrValue(Integer productId, Integer productAttributeId, Integer productAttributeValueId);

    /**
     * 新建商品属性值
     * @param productAttributeValuePO
     * @return
     */
    ProductAttributeValuePO addAttrValue(ProductAttributeValuePO productAttributeValuePO);

    /**
     * 获取商品规格集合
     * @param productId
     * @return
     */
    List<ProductSpecificationPO> getProductSpecificationByProductId(Integer productId);
}
