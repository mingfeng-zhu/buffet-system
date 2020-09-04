package cn.ffcs.buffet.service.impl;

import cn.ffcs.buffet.common.dto.Page;
import cn.ffcs.buffet.common.util.TokenUtil;
import cn.ffcs.buffet.mapper.ProductCategoryPOMapper;
import cn.ffcs.buffet.mapper.ProductPOMapper;
import cn.ffcs.buffet.mapper.ProductSpecificationPOMapper;
import cn.ffcs.buffet.mapper.UserPOMapper;
import cn.ffcs.buffet.model.dto.ProductManagerDTO;
import cn.ffcs.buffet.model.dto.UserDTO;
import cn.ffcs.buffet.model.po.ProductCategoryPO;
import cn.ffcs.buffet.service.ProductManageService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * @ClassName: ProductManageServiceImpl
 * @Author: chenzc
 * @Date: 2020/8/22
 * @Description:
 */
@Service
public class ProductManageServiceImpl implements ProductManageService {

    @Autowired
    private ProductCategoryPOMapper productCategoryPOMapper;

    @Autowired
    private ProductPOMapper productPOMapper;

    @Autowired
    private ProductSpecificationPOMapper productSpecificationPOMapper;

    @Autowired
    private UserPOMapper userPOMapper;


    /**
     * 获取商品分类
     * @param page
     * @param categoryName
     * @return
     */
    @Override
    public PageInfo<ProductCategoryPO> getProductCategoryList(Page page, String categoryName) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        List<ProductCategoryPO> list = productCategoryPOMapper.getProductCategoryList(categoryName);
        PageInfo<ProductCategoryPO> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public ProductCategoryPO getProductCategoryByproductCategoryId(Integer productCategoryId) {
        ProductCategoryPO productCategoryPO = productCategoryPOMapper.selectByPrimaryKey(productCategoryId);
        return productCategoryPO;
    }

    @Override
    public Integer updateProductCategory(ProductCategoryPO categoryPO) {
        Timestamp now = new Timestamp(new Date().getTime());
//        categoryPO.setCreateTime(now);
        categoryPO.setUpdateTime(now);
        Integer flag = productCategoryPOMapper.updateByPrimaryKeySelective(categoryPO);
        return flag;
    }

    @Override
    public Integer addProductCategory(ProductCategoryPO categoryPO) {
        Timestamp now = new Timestamp(new Date().getTime());
        categoryPO.setCreateTime(now);
        categoryPO.setUpdateTime(now);
        Integer productCategoryPO = productCategoryPOMapper.insertSelective(categoryPO);
        return productCategoryPO;
    }

    @Override
    public Integer deleteProductCategory(Integer productCategoryId) {
        Integer flag = productCategoryPOMapper.deleteByPrimaryKey(productCategoryId);
        return flag;
    }

    @Override
    public PageInfo<ProductManagerDTO> getProductList(Page page, String productName) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        List<ProductManagerDTO> list = productPOMapper.getProductList(productName);
        for (int i = 0; i < list.size(); i++) {
            Integer status = productSpecificationPOMapper.selectStatusByProductId(list.get(i).getProductId());
            list.get(i).setStatus(status);
        }
        PageInfo<ProductManagerDTO> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public Integer deleteProduct(Integer productId) {
        //将商品id为productId的规格删除（状态置为0）
        Integer flag = productSpecificationPOMapper.updateStatusByProductId(productId, 0);
        return flag;
    }

    @Override
    public Integer dropProduct(Integer productId) {
        //将商品id为productId的规格状态置为1
        Integer flag = productSpecificationPOMapper.updateStatusByProductId(productId, 1);
        return flag;
    }

    @Override
    public Integer upProduct(Integer productId) {
        //将商品id为productId的规格状态置为2
        Integer flag = productSpecificationPOMapper.updateStatusByProductId(productId, 2);
        return flag;
    }
}
