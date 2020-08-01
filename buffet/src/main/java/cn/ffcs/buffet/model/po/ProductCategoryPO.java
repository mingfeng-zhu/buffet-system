package cn.ffcs.buffet.model.po;

import io.swagger.annotations.ApiModel;

import java.io.Serializable;

/**
 * @Description 商品分类类
 * @Author chenzhenchao@ffcs.cn
 * @Date 2020/7/29 08:56
 */
@ApiModel
public class ProductCategoryPO extends BasePO implements Serializable {

    /**
     * 商品分类id
     */
    private Integer productCategoryId;

    /**
     * 商品分类名称
     */
    private String categoryName;

    /**
     * 商品分类描述
     */
    private String categoryDesc;

    /**
     * 父分类id
     */
    private Integer parentId;

    public Integer getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(Integer productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryDesc() {
        return categoryDesc;
    }

    public void setCategoryDesc(String categoryDesc) {
        this.categoryDesc = categoryDesc;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }
}