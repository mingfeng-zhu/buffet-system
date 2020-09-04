package cn.ffcs.buffet.controller.admin;

import cn.ffcs.buffet.common.annotation.PassToken;
import cn.ffcs.buffet.common.dto.Page;
import cn.ffcs.buffet.common.dto.Result;
import cn.ffcs.buffet.model.dto.ProductManagerDTO;
import cn.ffcs.buffet.model.po.ProductCategoryPO;
import cn.ffcs.buffet.service.ProductManageService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
public class ProductManageController {

    @Autowired
    private ProductManageService productManageService;

    @ApiOperation(value = "获取商品分类列表")
    @GetMapping(path = "/getProductCategoryList")
    @PassToken
    public Result getProductCategoryList(Page page, String categoryName) {
        PageInfo<ProductCategoryPO> productCategoryPOList = productManageService.getProductCategoryList(page, categoryName);
        page.setList(productCategoryPOList.getList());
        page.setTotal(productCategoryPOList.getTotal());
        return Result.success(page);
    }

    @ApiOperation(value = "查询商品分类")
    @GetMapping(path = "/productCategory/{productCategoryId}")
    @PassToken
    public Result getProductCategory(@PathVariable Integer productCategoryId) {
        ProductCategoryPO productCategoryPO = productManageService.getProductCategoryByproductCategoryId(productCategoryId);
        return Result.success(productCategoryPO);
    }

    @ApiOperation(value = "修改商品分类")
    @PutMapping(path = "/productCategory")
    @PassToken
    public Result updateProductCategory(@RequestBody ProductCategoryPO categoryPO) {
        Integer flag = productManageService.updateProductCategory(categoryPO);
        return Result.success(categoryPO);
    }

    @ApiOperation(value = "新建商品分类")
    @PostMapping(path = "/productCategory")
    @PassToken
    public Result addProductCategory(@RequestBody ProductCategoryPO categoryPO) {
        Integer flag = productManageService.addProductCategory(categoryPO);
        return Result.success(categoryPO);
    }

    @ApiOperation(value = "删除商品分类")
    @DeleteMapping(path = "/productCategory/{productCategoryId}")
    @PassToken
    public Result deleteProductCategory(@PathVariable Integer productCategoryId) {
        Integer flag = productManageService.deleteProductCategory(productCategoryId);
        return Result.success(flag);
    }

    @ApiOperation(value = "获取商品列表")
    @GetMapping(path = "/getProductList")
    @PassToken
    public Result getProductList(Page page, String productName) {
        PageInfo<ProductManagerDTO> productManagerDTOPageInfo = productManageService.getProductList(page, productName);
        page.setList(productManagerDTOPageInfo.getList());
        page.setTotal(productManagerDTOPageInfo.getTotal());
        return Result.success(page);
    }

    @ApiOperation(value = "删除商品")
    @DeleteMapping(path = "/deleteProduct/{productId}")
    @PassToken
    public Result deleteProduct(@PathVariable Integer productId) {
        Integer flag = productManageService.deleteProduct(productId);
        return Result.success(flag);
    }

    @ApiOperation(value = "下架商品")
    @PutMapping(path = "/dropProduct/{productId}")
    @PassToken
    public Result dropProduct(@PathVariable Integer productId) {
        Integer flag = productManageService.dropProduct(productId);
        return Result.success(flag);
    }

    @ApiOperation(value = "上架商品")
    @PutMapping(path = "/upProduct/{productId}")
    @PassToken
    public Result upProduct(@PathVariable Integer productId) {
        Integer flag = productManageService.upProduct(productId);
        return Result.success(flag);
    }

}
