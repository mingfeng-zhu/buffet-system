package cn.ffcs.buffet.service.impl;

import cn.ffcs.buffet.common.dto.Page;
import cn.ffcs.buffet.mapper.CategoryPOMapper;
import cn.ffcs.buffet.model.po.CategoryPO;
import cn.ffcs.buffet.service.CategoryService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Description: denmo
 * @Author: mingfeng.zhu@ffcs.cn
 * @Date: 2020/7/22 21:59
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    /**
     * 类别mapper接口
     */
    @Autowired
    CategoryPOMapper categoryPOMapper;

    /**
     * 插入一个类别
     *
     * @param categoryPO
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public Integer insert(CategoryPO categoryPO) {
//        categoryPO.setCreateBy(TokenUtil.getTokenUserUsername());
        return categoryPOMapper.insert(categoryPO);
    }

    /**
     * 返回全部类别分页列表
     *
     * @param page
     * @param name
     * @return
     */
    @Override
    public PageInfo<CategoryPO> listCategories(Page<CategoryPO> page, String name) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        List<CategoryPO> list = categoryPOMapper.listCategories(name);
        PageInfo<CategoryPO> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    /**
     * 根据id更新一个类别名称
     *
     * @param categoryPO
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public Integer updateById(CategoryPO categoryPO) {
//        categoryPO.setUpdateBy(TokenUtil.getTokenUserUsername());
        return categoryPOMapper.updateById(categoryPO);
    }


}