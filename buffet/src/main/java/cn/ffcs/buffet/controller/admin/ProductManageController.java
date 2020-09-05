package cn.ffcs.buffet.controller.admin;

import cn.ffcs.buffet.common.annotation.PassToken;
import cn.ffcs.buffet.common.dto.Page;
import cn.ffcs.buffet.common.dto.Result;
import cn.ffcs.buffet.model.dto.ProductAttrManagerDTO;
import cn.ffcs.buffet.model.dto.ProductManagerDTO;
import cn.ffcs.buffet.model.po.*;
import cn.ffcs.buffet.service.ProductManageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

    @PassToken
    @ApiOperation(value = "添加商品")
    @PutMapping(path = "/addProduct")
    public Result addProduct(@RequestBody Map<String, Object> map) throws JsonProcessingException {
        Object product = map.get("product");
        Object productSpecification = map.get("productSpecification");
        ObjectMapper objectMapper = new ObjectMapper();
        ProductPO productPO = objectMapper.readValue(objectMapper.writeValueAsString(product), ProductPO.class);
        ProductSpecificationPO productSpecificationPO = objectMapper.readValue(objectMapper.writeValueAsString(productSpecification), ProductSpecificationPO.class);
        productPO = productManageService.addProduct(productPO, productSpecificationPO);
        return Result.success(productPO);
    }

    @PassToken
    @ApiOperation(value = "修改商品")
    @PostMapping(path = "/updateProduct")
    public Result updateProduct(@RequestBody ProductPO productPO) {
        int i = productManageService.updateProduct(productPO);
        return Result.success(i);
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

    @PassToken
    @ApiOperation(value = "根据商品id获取商品属性及其属性值")
    @GetMapping(path = "/getProductAttributeListByProductId/{productId}")
    public Result getProductAttributeListByProductId(@PathVariable Integer productId){
        List<ProductAttrManagerDTO> productAttrManagerDTOList = productManageService.selectProductAttributeByProductId(productId);
        return Result.success(productAttrManagerDTOList);
    }

    @ApiOperation(value = "新建商品规格属性")
    @PostMapping(path = "/productAttribute")
    @PassToken
    public Result addProductAttribute(@RequestBody ProductAttributePO productAttributePO) {
        productAttributePO = productManageService.addProductAttribute(productAttributePO);
        return Result.success(productAttributePO);
    }

    @ApiOperation(value = "修改商品规格属性")
    @PutMapping(path = "/productAttribute")
    @PassToken
    public Result updateProductAttribute(@RequestBody ProductAttributePO productAttributePO) {
        Integer flag = productManageService.updateProductAttribute(productAttributePO);
        return Result.success(productAttributePO);
    }

    @ApiOperation(value = "删除商品规格属性")
    @DeleteMapping(path = "/productAttribute/{productAttributeId}")
    @PassToken
    public Result deleteProductAttribute(@PathVariable Integer productAttributeId) {
        Integer flag = productManageService.deleteProductAttribute(productAttributeId);
        return Result.success(flag);
    }

    @ApiOperation(value = "删除商品规格属性值")
    @DeleteMapping(path = "/productAttributeValue")
    @PassToken
    public Result deleteAttrValue(@RequestBody Map<String, Object> map) {
        Integer productId = (Integer) map.get("productId");
        Integer productAttributeId = (Integer) map.get("productAttributeId");
        Integer productAttributeValueId = (Integer) map.get("productAttributeValueId");
        Integer flag = productManageService.deleteAttrValue(productId, productAttributeId, productAttributeValueId);
        return Result.success(flag);
    }

    @ApiOperation(value = "新建商品规格属性值")
    @PutMapping(path = "/productAttributeValue")
    @PassToken
    public Result addAttrValue(@RequestBody ProductAttributeValuePO productAttributeValuePO) {
        productAttributeValuePO = productManageService.addAttrValue(productAttributeValuePO);
        return Result.success(productAttributeValuePO);
    }

    @ApiOperation(value = "修改商品规格属性值")
    @PostMapping(path = "/productAttributeValue")
    @PassToken
    public Result updateAttrValue(@RequestBody ProductAttributeValuePO productAttributeValuePO) {
        int i = productManageService.updateAttrValue(productAttributeValuePO);
        return Result.success(productAttributeValuePO);
    }

    @PassToken
    @ApiOperation(value = "根据商品id获取商品规格")
    @GetMapping(path = "/getProductSpecificationByProductId/{productId}")
    public Result getProductSpecificationByProductId(@PathVariable Integer productId){
        List<ProductSpecificationPO> productSpecificationPOList = productManageService.getProductSpecificationByProductId(productId);
        return Result.success(productSpecificationPOList);
    }

    @ApiOperation(value = "新建商品规格")
    @PostMapping(path = "/productSpecification")
    @PassToken
    public Result addSpecification(@RequestBody ProductSpecificationPO productSpecificationPO) {
        productSpecificationPO = productManageService.addSpecification(productSpecificationPO);
        return Result.success(productSpecificationPO);
    }

    @ApiOperation(value = "修改商品规格")
    @PutMapping(path = "/productSpecification")
    @PassToken
    public Result updateSpecification(@RequestBody ProductSpecificationPO productSpecificationPO) {
        productSpecificationPO = productManageService.updateSpecification(productSpecificationPO);
        return Result.success(productSpecificationPO);
    }

    @ApiOperation(value = "删除商品规格")
    @DeleteMapping(path = "/productSpecification/{productSpecificationId}")
    @PassToken
    public Result deleteSpecification(@PathVariable Integer productSpecificationId) {
        int i = productManageService.deleteSpecification(productSpecificationId);
        return Result.success(i);
    }
}
