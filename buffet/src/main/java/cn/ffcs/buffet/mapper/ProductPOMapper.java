package cn.ffcs.buffet.mapper;

import cn.ffcs.buffet.model.dto.ProductDTO;
import cn.ffcs.buffet.model.po.ProductPO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description ProductMapper
 * @Author chenzhenchao@ffcs.cn
 * @Date 2020/7/29 15:55
 */
@Component
public interface ProductPOMapper {
    int deleteByPrimaryKey(Integer productId);

    int insert(ProductPO record);

    int insertSelective(ProductPO record);

    ProductPO selectByPrimaryKey(Integer productId);

    int updateByPrimaryKeySelective(ProductPO record);

    int updateByPrimaryKey(ProductPO record);

    /**
     * 根据商品分类id获取商品列表
     * @param productCategoryId
     * @return
     */
    List<ProductDTO> selectProductByProductCategoryId(@Param("productCategoryId") Integer productCategoryId);

    /**
     * 根据商品名称获取商品
     * @param productName
     * @return
     */
    List<ProductDTO> selectProductByProductName(@Param("productName") String productName);

    /**
     * 获取全部商品
     * @return
     */
    List<ProductDTO> selectAllProductList();
}