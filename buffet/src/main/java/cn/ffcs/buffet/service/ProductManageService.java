package cn.ffcs.buffet.service;

import cn.ffcs.buffet.common.dto.Page;
import cn.ffcs.buffet.model.po.ProductCategoryPO;
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
}
