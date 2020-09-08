package cn.ffcs.buffet.mapper;

import cn.ffcs.buffet.model.dto.ProductAttributeValueDTO;
import cn.ffcs.buffet.model.po.ProductAttributeValuePO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description ProductAttributeValueMapper
 * @Author chenzhenchao@ffcs.cn
 * @Date 2020/7/29 15:55
 */
@Component
public interface ProductAttributeValuePOMapper {
    int deleteByPrimaryKey(Integer productAttributeValueId);

    int insert(ProductAttributeValuePO record);

    int insertSelective(ProductAttributeValuePO record);

    ProductAttributeValuePO selectByPrimaryKey(Integer productAttributeValueId);

    int updateByPrimaryKeySelective(ProductAttributeValuePO record);

    int updateByPrimaryKey(ProductAttributeValuePO record);

    List<ProductAttributeValueDTO> selectProductAttributeValueByProductAttributeId(@Param("productAttributeId") Integer productAttributeId);

    /**
     * 根虎属性id删除属性值
     * @param productAttributeId
     * @return
     */
    int deleteByProductAttributeId(@Param("productAttributeId") Integer productAttributeId);

    List<ProductAttributeValuePO> selectValueByProductAttributeId(@Param("productAttributeId") Integer productAttributeId);
}