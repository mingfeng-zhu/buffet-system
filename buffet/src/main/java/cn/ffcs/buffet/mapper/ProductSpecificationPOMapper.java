package cn.ffcs.buffet.mapper;

import cn.ffcs.buffet.model.dto.ProductSpecificationDTO;
import cn.ffcs.buffet.model.po.ProductSpecificationPO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description ProductSpecificationMapper
 * @Author chenzhenchao@ffcs.cn
 * @Date 2020/7/29 15:55
 */
@Component
public interface ProductSpecificationPOMapper {
    int deleteByPrimaryKey(Integer productSpecificationId);

    int insert(ProductSpecificationPO record);

    int insertSelective(ProductSpecificationPO record);

    ProductSpecificationPO selectByPrimaryKey(Integer productSpecificationId);

    int updateByPrimaryKeySelective(ProductSpecificationPO record);

    int updateByPrimaryKey(ProductSpecificationPO record);

    List<ProductSpecificationDTO> selectSpecificationByProductId(@Param("productId") Integer productId);

    /**
     * 根据商品id和商品规格查询商品规格详细信息
     * @param productId
     * @param productSpecification
     * @return
     */
    ProductSpecificationDTO selectSpecificationByProductIdAndSpecification(@Param("productId") Integer productId, @Param("productSpecification") String productSpecification);

    /**
     * 根据商品规格id返回商品规格详情
     * @param productSpecificationId
     * @return
     */
    ProductSpecificationDTO selectSpecificationByProductSpecificationId(@Param("productSpecificationId") Integer productSpecificationId);

    /**
     * 根据商品规格id集合返回商品规格集合
     * @param productSpecificationIdList
     * @return
     */
    List<ProductSpecificationDTO> selectSpecificationByProductSpecificationIdList(@Param("productSpecificationIdList") List<Integer> productSpecificationIdList);

    /**
     * 修改商品储存量, 修改库存量（订单支付时）
     * @param productSpecificationId
     * @param number
     * @return
     */
    Integer updateProductStorage(@Param("productSpecificationId") Integer productSpecificationId, @Param("number") Integer number);
}