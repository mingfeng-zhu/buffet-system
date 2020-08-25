package cn.ffcs.buffet.controller.admin;

import cn.ffcs.buffet.common.annotation.PassToken;
import cn.ffcs.buffet.common.dto.Result;
import cn.ffcs.buffet.model.dto.ProductCategoryDTO;
import cn.ffcs.buffet.model.po.ProductCategoryPO;
import cn.ffcs.buffet.service.ProductManageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName: ProductManageController
 * @Author: chenzc
 * @Date: 2020/8/22
 * @Description:
 */
@Api(value = "/admin/product", tags = "商品管理后台服务")
@RestController
@RequestMapping(value = "/admin/product")
@Validated
@CrossOrigin
public class ProductManageController {

    @Autowired
    private ProductManageService productManageService;

    @ApiOperation(value = "获取商品分类列表")
    @GetMapping(path = "/getProductCategoryList")
    @PassToken
    public Result getProductCategoryList() {
        List<ProductCategoryPO> productCategoryPOList = productManageService.getProductCategoryList();
        return Result.success(productCategoryPOList);
    }

    @ApiOperation(value = "查询商品分类")
    @GetMapping(path = "/productCategory/{productCategoryId}")
    @PassToken
    public Result updateProductCategory(@PathVariable Integer productCategoryId) {
        ProductCategoryPO productCategoryPO = productManageService.getProductCategoryByproductCategoryId(productCategoryId);
        return Result.success(productCategoryPO);
    }

    @ApiOperation(value = "修改商品分类")
    @PostMapping(path = "/productCategory")
    @PassToken
    public Result updateProductCategory(@RequestBody ProductCategoryPO categoryPO) {
        Integer flag = productManageService.updateProductCategory(categoryPO);
        return Result.success(categoryPO);
    }

    @ApiOperation(value = "新建商品分类")
    @PutMapping(path = "/productCategory")
    @PassToken
    public Result addProductCategory(@RequestBody ProductCategoryPO categoryPO) {
        Integer flag = productManageService.addProductCategory(categoryPO);
        return Result.success(categoryPO);
    }

    @ApiOperation(value = "新建商品分类")
    @DeleteMapping(path = "/productCategory/{productCategoryId}")
    @PassToken
    public Result deleteProductCategory(@PathVariable Integer productCategoryId) {
        Integer flag = productManageService.deleteProductCategory(productCategoryId);
        return Result.success(flag);
    }


}
