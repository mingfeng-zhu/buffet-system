package cn.ffcs.buffet.mapper;

import cn.ffcs.buffet.model.dto.ProductCategoryDTO;
import cn.ffcs.buffet.model.po.ProductCategoryPO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description ProductCategoryMapper
 * @Author chenzhenchao@ffcs.cn
 * @Date 2020/7/29 15:55
 */
@Component
public interface ProductCategoryPOMapper {
    int deleteByPrimaryKey(Integer productCategoryId);

    int insert(ProductCategoryPO record);

    int insertSelective(ProductCategoryPO record);

    ProductCategoryPO selectByPrimaryKey(Integer productCategoryId);

    int updateByPrimaryKeySelective(ProductCategoryPO record);

    int updateByPrimaryKey(ProductCategoryPO record);

    List<ProductCategoryDTO> selectProductCategoryList();

    /**
     * 获取商品分类
     * @param categoryName
     * @return
     */
    List<ProductCategoryPO> getProductCategoryList(@Param("categoryName") String categoryName);
}