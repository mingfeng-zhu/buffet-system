package cn.ffcs.buffet.model.dto;

import cn.ffcs.buffet.model.po.ProductAttributePO;
import cn.ffcs.buffet.model.po.ProductAttributeValuePO;

import java.util.List;

/**
 * @ClassName: ProductAttrManagerDTO
 * @Author: chenzc
 * @Date: 2020/9/5
 * @Description:
 */
public class ProductAttrManagerDTO extends ProductAttributePO {

    private List<ProductAttributeValuePO> productAttributeValuePOList;

    public List<ProductAttributeValuePO> getProductAttributeValuePOList() {
        return productAttributeValuePOList;
    }

    public void setProductAttributeValuePOList(List<ProductAttributeValuePO> productAttributeValuePOList) {
        this.productAttributeValuePOList = productAttributeValuePOList;
    }
}
