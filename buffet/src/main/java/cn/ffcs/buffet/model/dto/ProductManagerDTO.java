package cn.ffcs.buffet.model.dto;

import cn.ffcs.buffet.model.po.ProductPO;

/**
 * @ClassName: ProductManagerDTO
 * @Author: chenzc
 * @Date: 2020/9/1
 * @Description:
 */
public class ProductManagerDTO extends ProductPO {

    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
