package cn.ffcs.buffet.mapper;

import cn.ffcs.buffet.model.po.CategoryPO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description: 分类映射类
 * @Author: mingfeng.zhu@ffcs.cn
 * @Date: 2020/7/22 21:29
 */
@Component
public interface CategoryPOMapper {
    /**
     * 获取商品类别分页列表
     *
     * @param name
     * @return
     */
    List<CategoryPO> listCategories(@Param("name") String name);

    /**
     * 插入商品类别信息
     *
     * @param record
     * @return
     */
    Integer insert(CategoryPO record);

    /**
     * 更新类别信息
     *
     * @param categoryPO
     * @return
     */
    Integer updateById(CategoryPO categoryPO);

}