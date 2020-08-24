package cn.ffcs.buffet.service.impl;

import cn.ffcs.buffet.common.util.TokenUtil;
import cn.ffcs.buffet.mapper.ProductCategoryPOMapper;
import cn.ffcs.buffet.mapper.UserPOMapper;
import cn.ffcs.buffet.model.dto.UserDTO;
import cn.ffcs.buffet.model.po.ProductCategoryPO;
import cn.ffcs.buffet.service.ProductManageService;
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
    private UserPOMapper userPOMapper;


    @Override
    public List<ProductCategoryPO> getProductCategoryList() {
        return productCategoryPOMapper.getProductCategoryList();
    }

    @Override
    public ProductCategoryPO getProductCategoryByproductCategoryId(Integer productCategoryId) {
        ProductCategoryPO productCategoryPO = productCategoryPOMapper.selectByPrimaryKey(productCategoryId);
        return productCategoryPO;
    }

    @Override
    public Integer updateProductCategory(ProductCategoryPO categoryPO) {
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
}
