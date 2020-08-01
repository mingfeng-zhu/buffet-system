package cn.ffcs.buffet.mapper;

import cn.ffcs.buffet.model.dto.ProductAttributeDTO;
import cn.ffcs.buffet.model.po.ProductAttributePO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description ProductAttributeMapper
 * @Author chenzhenchao@ffcs.cn
 * @Date 2020/7/29 15:55
 */
@Component
public interface ProductAttributePOMapper {
    int deleteByPrimaryKey(Integer productAttributeId);

    int insert(ProductAttributePO record);

    int insertSelective(ProductAttributePO record);

    ProductAttributePO selectByPrimaryKey(Integer productAttributeId);

    int updateByPrimaryKeySelective(ProductAttributePO record);

    int updateByPrimaryKey(ProductAttributePO record);

    List<ProductAttributeDTO> selectProductAttributeListByProductId(@Param("productId") Integer productId);
}