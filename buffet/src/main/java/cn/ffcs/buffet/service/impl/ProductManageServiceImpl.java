package cn.ffcs.buffet.service.impl;

import cn.ffcs.buffet.common.dto.Page;
import cn.ffcs.buffet.common.util.TokenUtil;
import cn.ffcs.buffet.mapper.*;
import cn.ffcs.buffet.model.dto.ProductAttrManagerDTO;
import cn.ffcs.buffet.model.dto.ProductAttributeDTO;
import cn.ffcs.buffet.model.dto.ProductManagerDTO;
import cn.ffcs.buffet.model.dto.UserDTO;
import cn.ffcs.buffet.model.po.*;
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
    private ProductAttributePOMapper productAttributePOMapper;

    @Autowired
    private ProductAttributeValuePOMapper productAttributeValuePOMapper;

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
    public ProductPO addProduct(ProductPO productPO, ProductSpecificationPO productSpecificationPO) {
        Timestamp now = new Timestamp(new Date().getTime());
        productPO.setCreateTime(now);
        productPO.setUpdateTime(now);

        int i = productPOMapper.insertSelective(productPO);

        productSpecificationPO.setProductId(productPO.getProductId());
        productSpecificationPO.setCreateTime(now);
        productSpecificationPO.setUpdateTime(now);
        productSpecificationPO.setStatus(2);

        i += productSpecificationPOMapper.insertSelective(productSpecificationPO);

        return productPOMapper.selectByPrimaryKey(productPO.getProductId());
    }

    @Override
    public int updateProduct(ProductPO productPO) {
        productPO.setUpdateTime(new Timestamp(new Date().getTime()));
        int i = productPOMapper.updateByPrimaryKeySelective(productPO);
        return i;
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

    @Override
    public List<ProductAttrManagerDTO> selectProductAttributeByProductId(Integer productId) {
        List<ProductAttrManagerDTO> productAttrManagerDTOList = this.productAttributePOMapper.selectAttrAndValueByProductId(productId);
        return productAttrManagerDTOList;
    }

    @Override
    public ProductAttributePO addProductAttribute(ProductAttributePO productAttributePO) {
        productAttributePO.setCreateTime(new Timestamp(new Date().getTime()));
        productAttributePO.setUpdateTime(new Timestamp(new Date().getTime()));
        int i = this.productAttributePOMapper.insertSelective(productAttributePO);
        return productAttributePOMapper.selectByPrimaryKey(productAttributePO.getProductAttributeId());
    }

    @Override
    public Integer updateProductAttribute(ProductAttributePO productAttributePO) {
        int flag = 0;

        ProductAttributePO old = productAttributePOMapper.selectByPrimaryKey(productAttributePO.getProductAttributeId());
        List<ProductSpecificationPO> productSpecificationPOList = productSpecificationPOMapper.selectBySpecification(old.getProductId(), old.getProductAttributeName());
        for (int i = 0; i < productSpecificationPOList.size(); i++) {
            int index = productSpecificationPOList.get(i).getProductSpecification().indexOf(old.getProductAttributeName());
            String tmp = productSpecificationPOList.get(i).getProductSpecification();
            tmp = tmp.substring(0, index) + productAttributePO.getProductAttributeName() + tmp.substring(index + old.getProductAttributeName().length());
            productSpecificationPOList.get(i).setProductSpecification(tmp);
            flag += productSpecificationPOMapper.updateByPrimaryKeySelective(productSpecificationPOList.get(i));
        }
        productAttributePO.setUpdateTime(new Timestamp(new Date().getTime()));
        flag = this.productAttributePOMapper.updateByPrimaryKeySelective(productAttributePO);
        return flag;
    }

    @Override
    public Integer deleteProductAttribute(Integer productAttributeId) {
        int flag = 0;
        ProductAttributePO productAttributePO = productAttributePOMapper.selectByPrimaryKey(productAttributeId);
        // 删除规格
        List<ProductSpecificationPO> productSpecificationPOList = productSpecificationPOMapper.selectBySpecification(productAttributePO.getProductId(), productAttributePO.getProductAttributeName());
        for (int i = 0; i < productSpecificationPOList.size(); i++) {
            productSpecificationPOList.get(i).setStatus(0);
            flag += productSpecificationPOMapper.updateByPrimaryKeySelective(productSpecificationPOList.get(i));
        }
        // 删除属性值
        flag += this.productAttributeValuePOMapper.deleteByProductAttributeId(productAttributeId);
        // 删除属性
        flag += this.productAttributePOMapper.deleteByPrimaryKey(productAttributeId);
        return flag;
    }

    @Override
    public Integer deleteAttrValue(Integer productId, Integer productAttributeId, Integer productAttributeValueId) {
        Integer flag = 0;
        ProductAttributePO productAttributePO = productAttributePOMapper.selectByPrimaryKey(productAttributeId);    // 获取规格属性
        ProductAttributeValuePO productAttributeValuePO = productAttributeValuePOMapper.selectByPrimaryKey(productAttributeValueId);    // 获取规格属性值

        // 删除商品规格表
        String specification = "\"" +  productAttributePO.getProductAttributeName() + "\"" + ":" + "\"" + productAttributeValuePO.getProductAttributeValue() + "\"";
        List<ProductSpecificationPO> productSpecificationPOList = productSpecificationPOMapper.selectBySpecification(productId, specification);
        for (int i = 0; i < productSpecificationPOList.size(); i++) {
            /*int index = productSpecificationPOList.get(i).getProductSpecification().indexOf(specification);
            String tmp = productSpecificationPOList.get(i).getProductSpecification();
            if (index == 1) {
                tmp = tmp.substring(0, index) + tmp.substring(index + specification.length() + 1);
            } else if (index > 1) {
                tmp = tmp.substring(0, index - 1) + tmp.substring(index + specification.length());
            }
            productSpecificationPOList.get(i).setProductSpecification(tmp);*/
            productSpecificationPOList.get(i).setStatus(0);
            flag += productSpecificationPOMapper.updateByPrimaryKeySelective(productSpecificationPOList.get(i));
        }
        // 删除属性值
        flag += productAttributeValuePOMapper.deleteByPrimaryKey(productAttributeValueId);
        return flag;
    }

    @Override
    public ProductAttributeValuePO addAttrValue(ProductAttributeValuePO productAttributeValuePO) {
        productAttributeValuePO.setCreateTime(new Timestamp(new Date().getTime()));
        productAttributeValuePO.setUpdateTime(new Timestamp(new Date().getTime()));
        int i = productAttributeValuePOMapper.insertSelective(productAttributeValuePO);
        return productAttributeValuePO;
    }

    @Override
    public int updateAttrValue(ProductAttributeValuePO productAttributeValuePO) {
        int flag = 0;

        ProductAttributePO productAttributePO = productAttributePOMapper.selectByPrimaryKey(productAttributeValuePO.getProductAttributeId());
        ProductAttributeValuePO old = productAttributeValuePOMapper.selectByPrimaryKey(productAttributeValuePO.getProductAttributeValueId());

        String specification = "\"" +  productAttributePO.getProductAttributeName() + "\"" + ":" + "\"" + old.getProductAttributeValue() + "\"";
        List<ProductSpecificationPO> productSpecificationPOList = productSpecificationPOMapper.selectBySpecification(productAttributePO.getProductId(), specification);
        for (int i = 0; i < productSpecificationPOList.size(); i++) {
            int index = productSpecificationPOList.get(i).getProductSpecification().indexOf(old.getProductAttributeValue());
            String tmp = productSpecificationPOList.get(i).getProductSpecification();
            tmp = tmp.substring(0, index) + productAttributeValuePO.getProductAttributeValue() + tmp.substring(index + old.getProductAttributeValue().length());
            productSpecificationPOList.get(i).setProductSpecification(tmp);
            flag += productSpecificationPOMapper.updateByPrimaryKeySelective(productSpecificationPOList.get(i));
        }
        flag += productAttributeValuePOMapper.updateByPrimaryKeySelective(productAttributeValuePO);
        return flag;
    }

    @Override
    public List<ProductSpecificationPO> getProductSpecificationByProductId(Integer productId) {
        List<ProductSpecificationPO> productSpecificationPOList = productSpecificationPOMapper.selectByProductId(productId);
        return productSpecificationPOList;
    }

    @Override
    public ProductSpecificationPO addSpecification(ProductSpecificationPO productSpecificationPO) {
        int flag = 0;
        List<ProductSpecificationPO> productSpecificationPOList = productSpecificationPOMapper.selectBySpecification(productSpecificationPO.getProductId(), "{}");
        for (int i = 0; i < productSpecificationPOList.size(); i++) {
            if ("{}".equals(productSpecificationPOList.get(i).getProductSpecification())) {
                productSpecificationPOList.get(i).setStatus(0);
                flag += productSpecificationPOMapper.updateByPrimaryKeySelective(productSpecificationPOList.get(i));
            }
        }

        productSpecificationPO.setStatus(2);
        productSpecificationPO.setUpdateTime(new Timestamp(new Date().getTime()));
        productSpecificationPO.setCreateTime(new Timestamp(new Date().getTime()));
        flag += productSpecificationPOMapper.insertSelective(productSpecificationPO);
        return productSpecificationPO;
    }

    @Override
    public ProductSpecificationPO updateSpecification(ProductSpecificationPO productSpecificationPO) {
        productSpecificationPO.setUpdateTime(new Timestamp(new Date().getTime()));
        int i = productSpecificationPOMapper.updateByPrimaryKeySelective(productSpecificationPO);
        return productSpecificationPO;
    }

    @Override
    public int deleteSpecification(Integer productSpecificationId) {
        ProductSpecificationPO productSpecificationPO = productSpecificationPOMapper.selectByPrimaryKey(productSpecificationId);
        productSpecificationPO.setStatus(0);
        int i = productSpecificationPOMapper.updateByPrimaryKeySelective(productSpecificationPO);
        return i;
    }
}
