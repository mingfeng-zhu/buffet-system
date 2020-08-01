package cn.ffcs.buffet.controller;

import cn.ffcs.buffet.common.dto.Result;
import cn.ffcs.buffet.model.dto.ProductAttributeDTO;
import cn.ffcs.buffet.model.dto.ProductCategoryDTO;
import cn.ffcs.buffet.model.dto.ProductDTO;
import cn.ffcs.buffet.model.dto.ProductSpecificationDTO;
import cn.ffcs.buffet.service.ProductModuleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description ProductModuleController
 * @Author chenzhenchao@ffcs.cn
 * @Date 2020/7/29 16:14
 */
@Api(value = "/api/product", tags = "商品服务")
@RestController
@RequestMapping(value = "/api/product")
@Validated
public class ProductModuleController {

    @Autowired
    private ProductModuleService productModuleService;

    @ApiOperation(value = "获取商品分类列表")
    @GetMapping(path = "/getProductCategoryList")
    public Result getProductCategoryList() {
        List<ProductCategoryDTO> productCategoryDTOList = productModuleService.selectProductCategoryList();
        return Result.success(productCategoryDTOList);
    }

    @ApiOperation(value = "根据商品分类id获取商品列表")
    @GetMapping(path = "/getProductListByProductCategoryId/{productCategoryId}")
    public Result getProductListByProductCategoryId(@PathVariable Integer productCategoryId) {
        List<ProductDTO> productDTOList = productModuleService.selectProductListByProductCategoryId(productCategoryId);
        return Result.success(productDTOList);
    }

    @ApiOperation(value = "根据商品名称查询商品")
    @GetMapping(path = "/getProductListByProductName/{productName}")
    public Result getProductListByProductName(@PathVariable String productName){
        List<ProductDTO> productDTOList = productModuleService.selectProductListByProductName(productName);
        return Result.success(productDTOList);
    }

    @ApiOperation(value = "根据商品id获取商品属性及其属性值")
    @GetMapping(path = "/getProductAttributeListByProductId/{productId}")
    public Result getProductAttributeListByProductId(@PathVariable Integer productId){
        List<ProductAttributeDTO> productAttributeDTOList = productModuleService.selectProductAttributeByProductId(productId);
        return Result.success(productAttributeDTOList);
    }

    @ApiOperation(value = "根据商品id和商品规格获取该商品的详细信息")
    @GetMapping(path = "/getSpecificationByProductIdAndSpecification")
    public Result getSpecificationByProductIdAndSpecification(@RequestParam(name = "productId") Integer productId, @RequestParam(name = "productSpecification") String productSpecification){
        ProductSpecificationDTO productSpecificationDTO = productModuleService.selectSpecificationByProductIdAndSpecification(productId, productSpecification);
        return Result.success(productSpecificationDTO);
    }
}