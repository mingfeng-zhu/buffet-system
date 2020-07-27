package cn.ffcs.buffet.controller;

import cn.ffcs.buffet.common.dto.Page;
import cn.ffcs.buffet.common.dto.Result;
import cn.ffcs.buffet.model.po.CategoryPO;
import cn.ffcs.buffet.service.CategoryService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Description: demo
 * @Author: mingfeng.zhu@ffcs.cn
 * @Date: 2020/7/21 10:29
 */
@Api(value = "/api/demo", tags = "demo服务")
@RestController
@RequestMapping(value = "/api/demo")
@Validated
public class DemoController {
    /**
     * 类别服务
     */
    @Autowired
    CategoryService categoryService;

    /**
     * demo****
     *
     * @return
     */
    @ApiOperation(value = "获取index", notes = "首页")
    @GetMapping(path = "/index")
    public Result Index() {
        int i = 1 / 0;
        return Result.success();
    }


    /**
     * 分页获取类别列表
     *
     * @param page
     * @param name
     * @return
     */
    @ApiOperation(value = "获取类别列表", notes = "获取所有类别信息")
    @GetMapping(path = "/list")
    public Result listCategories(Page<CategoryPO> page, String name) {

        PageInfo<CategoryPO> categoryList = categoryService.listCategories(page, name);
        if (categoryList.getTotal() > 0) {
            page.setList(categoryList.getList());
            page.setTotal(categoryList.getTotal());
            return Result.success(page);
        }
        return Result.success();

    }


    /**
     * 添加商品类别
     *
     * @param categoryPO 类别类
     * @return
     */
    @PostMapping(path = "/add")
    public Result insertCatetory(@RequestBody @Valid CategoryPO categoryPO) {
        System.out.println(categoryPO);
        categoryService.insert(categoryPO);
        return Result.success();

    }

    /**
     * 更新类别信息
     *
     * @param categoryPO 类别类
     * @return
     */
    @PutMapping(path = "/update")
    public Result updateById(@RequestBody @Valid CategoryPO categoryPO) {
        categoryService.updateById(categoryPO);
        return Result.success();
    }

    /**
     * 根据id删除类别，并删除其下所有商品
     *
     * @param id 类别id
     * @return
     */
    @DeleteMapping(path = "/delete/{id}")
    public Result deleteCategoryDo(@PathVariable int id) {
        return Result.success();
    }
}
