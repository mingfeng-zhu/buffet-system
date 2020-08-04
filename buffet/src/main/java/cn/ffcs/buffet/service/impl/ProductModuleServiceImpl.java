package cn.ffcs.buffet.service.impl;

import cn.ffcs.buffet.common.dto.Page;
import cn.ffcs.buffet.mapper.ProductAttributePOMapper;
import cn.ffcs.buffet.mapper.ProductCategoryPOMapper;
import cn.ffcs.buffet.mapper.ProductPOMapper;
import cn.ffcs.buffet.mapper.ProductSpecificationPOMapper;
import cn.ffcs.buffet.model.dto.ProductAttributeDTO;
import cn.ffcs.buffet.model.dto.ProductCategoryDTO;
import cn.ffcs.buffet.model.dto.ProductDTO;
import cn.ffcs.buffet.model.dto.ProductSpecificationDTO;
import cn.ffcs.buffet.model.po.ProductPO;
import cn.ffcs.buffet.service.ProductModuleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description ProductModuleServiceImpl
 * @Author chenzhenchao@ffcs.cn
 * @Date 2020/7/29 15:42
 */
@Service
public class ProductModuleServiceImpl implements ProductModuleService {

    @Autowired
    private ProductCategoryPOMapper productCategoryMapper;

    @Autowired
    private ProductPOMapper productPOMapper;

    @Autowired
    private ProductAttributePOMapper productAttributePOMapper;

    @Autowired
    private ProductSpecificationPOMapper productSpecificationPOMapper;

    /**
     * 查询所有商品分类,及分类中的商品
     * @return
     */
    @Override
    public List<ProductCategoryDTO> selectProductCategoryList() {
        List<ProductCategoryDTO> productCategoryDTOList = this.productCategoryMapper.selectProductCategoryList();
        return productCategoryDTOList;
    }

    /**
     * 根据商品分类id获取商品列表
     * @param page
     * @param productCategoryId
     * @return
     */
    @Override
    public PageInfo<ProductDTO> selectProductListByProductCategoryId(Page<ProductDTO> page, Integer productCategoryId) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        List<ProductDTO> productDTOList = this.productPOMapper.selectProductByProductCategoryId(productCategoryId);
        PageInfo<ProductDTO> pageInfo = new PageInfo<>(productDTOList);
        return pageInfo;
    }

    /**
     * 根据商品名称查询商品
     * @param productName
     * @return
     */
    @Override
    public PageInfo<ProductDTO> selectProductListByProductName(Page<ProductDTO> page, String productName) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        List<ProductDTO> productDTOList = this.productPOMapper.selectProductByProductName(productName);
        PageInfo<ProductDTO> pageInfo = new PageInfo<>(productDTOList);
        return pageInfo;
    }

    /**
     * 根据商品id获取商品属性列表
     * @param productId
     * @return
     */
    @Override
    public List<ProductAttributeDTO> selectProductAttributeByProductId(Integer productId) {
        List<ProductAttributeDTO> productAttributeDTOList = this.productAttributePOMapper.selectProductAttributeListByProductId(productId);
        return productAttributeDTOList;
    }

    /**
     * 根据商品id和商品规格获取该商品规格的详细信息
     * @param productId
     * @param productSpecification
     * @return
     */
    @Override
    public ProductSpecificationDTO selectSpecificationByProductIdAndSpecification(Integer productId, String productSpecification) {
        ProductSpecificationDTO productSpecificationDTO = this.productSpecificationPOMapper.selectSpecificationByProductIdAndSpecification(productId, productSpecification);
        return productSpecificationDTO;
    }

    /**
     * 获取全部商品
     * @param page
     * @return
     */
    @Override
    public PageInfo<ProductDTO> selectAllProductList(Page<ProductDTO> page) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        List<ProductDTO> productDTOList = this.productPOMapper.selectAllProductList();
        PageInfo<ProductDTO> pageInfo = new PageInfo<>(productDTOList);
        return pageInfo;
    }

    /**
     * 根据商品规格id集合返回商品规格集合
     * @param productSpecificationIdList
     * @return
     */
    @Override
    public List<ProductSpecificationDTO> selectSpecificationByProductSpecificationIdList(List<Integer> productSpecificationIdList) {
        List<ProductSpecificationDTO> productSpecificationDTOList = this.productSpecificationPOMapper.selectSpecificationByProductSpecificationIdList(productSpecificationIdList);
        return productSpecificationDTOList;
    }

    /**
     * 修改商品储存量, 库存量 - 1（订单支付时）
     * @param productSpecificationIdList
     * @return
     */
    @Override
    public Integer updateProductStorageToMinusOne(List<Integer> productSpecificationIdList) {
        Integer flag = this.productSpecificationPOMapper.updateProductStorageToMinusOne(productSpecificationIdList);
        return flag;
    }
}
