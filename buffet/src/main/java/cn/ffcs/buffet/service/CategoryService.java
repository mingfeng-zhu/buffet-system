package cn.ffcs.buffet.service;

import cn.ffcs.buffet.common.dto.Page;
import cn.ffcs.buffet.model.po.CategoryPO;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @Description: demo
 * @Author: mingfeng.zhu@ffcs.cn
 * @Date: 2020/7/22 21:57
 */
public interface CategoryService {

    /**
     * 获取商品类别分页列表
     *
     * @param page
     * @param name
     * @return
     */
    PageInfo<CategoryPO> listCategories(Page<CategoryPO> page, String name);

    /**
     * 插入商品类别信息
     *
     * @param categoryPO
     * @return
     */
    Integer insert(CategoryPO categoryPO);

    /**
     * 更新类别信息
     *
     * @param categoryPO
     * @return
     */
    Integer updateById(CategoryPO categoryPO);

}