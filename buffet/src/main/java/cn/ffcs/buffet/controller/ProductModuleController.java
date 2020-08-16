package cn.ffcs.buffet.controller;

import cn.ffcs.buffet.common.dto.Page;
import cn.ffcs.buffet.common.dto.Result;
import cn.ffcs.buffet.model.dto.*;
import cn.ffcs.buffet.service.ProductModuleService;
import cn.ffcs.buffet.service.ShopCartService;
import com.github.pagehelper.PageInfo;
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

    @Autowired
    private ShopCartService shopCartService;

    @ApiOperation(value = "获取商品分类列表")
    @GetMapping(path = "/getProductCategoryList")
    public Result getProductCategoryList() {
        List<ProductCategoryDTO> productCategoryDTOList = productModuleService.selectProductCategoryList();
        return Result.success(productCategoryDTOList);
    }

    @ApiOperation(value = "根据商品分类id获取商品列表")
    @GetMapping(path = "/getProductListByProductCategoryId")
    public Result getProductListByProductCategoryId(Page<ProductDTO> page, Integer productCategoryId) {
        Integer userId = 1;

        PageInfo<ProductDTO> productDTOList = productModuleService.selectProductListByProductCategoryId(page, productCategoryId);
        if(productDTOList.getTotal() > 0){

            List<ProductDTO> list = productDTOList.getList();
            List<ShopCartDetailDTO> shopCartDetailDTOList = (List<ShopCartDetailDTO>)shopCartService.listShopCartByUserId(userId).getData();

            for (int i = 0; i < list.size(); i++) {
                for (int j = 0; j < shopCartDetailDTOList.size(); j++) {
                    if(list.get(i).getProductId() == shopCartDetailDTOList.get(j).getProductSpecificationDTO().getProductId()){
                        if(list.get(i).getProductNumOfCart() == null){
                            list.get(i).setProductNumOfCart(0);
                        }
                        list.get(i).setProductNumOfCart(list.get(i).getProductNumOfCart() + shopCartDetailDTOList.get(j).getShopCart().getGoodCount());
                    }
                }
            }

            page.setList(productDTOList.getList());
            page.setTotal(productDTOList.getTotal());
            return Result.success(page);
        }
        return Result.success();
    }

    @ApiOperation(value = "根据商品名称查询商品")
    @GetMapping(path = "/getProductListByProductName")
    public Result getProductListByProductName(Page<ProductDTO> page, String productName){
        PageInfo<ProductDTO> productDTOList = productModuleService.selectProductListByProductName(page, productName);
        if(productDTOList.getTotal() > 0){
            page.setList(productDTOList.getList());
            page.setTotal(productDTOList.getTotal());
            return Result.success(page);
        }
        return Result.success();
    }

    @ApiOperation(value = "获取全部商品")
    @GetMapping(path = "/getAllProductList")
    public Result getAllProductList(Page<ProductDTO> page){
        PageInfo<ProductDTO> productDTOList = productModuleService.selectAllProductList(page);
        if(productDTOList.getTotal() > 0){
            page.setList(productDTOList.getList());
            page.setTotal(productDTOList.getTotal());
            return Result.success(page);
        }
        return Result.success();
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

    @ApiOperation(value = "根据商品id获取商品详细信息以及商品评价")
    @GetMapping(path = "/getProductDetailAndCommentList/{productId}")
    public Result getProductDetailAndCommentList(@PathVariable Integer productId){
        List<ProductDTO> productDTOList = productModuleService.selectProductDetailAndCommentList(productId);
        return Result.success(productDTOList);
    }
}