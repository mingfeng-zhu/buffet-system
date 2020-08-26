package cn.ffcs.buffet.controller;

import cn.ffcs.buffet.common.annotation.PassToken;
import cn.ffcs.buffet.common.dto.Page;
import cn.ffcs.buffet.common.dto.Result;
import cn.ffcs.buffet.common.util.TokenUtil;
import cn.ffcs.buffet.model.dto.*;
import cn.ffcs.buffet.model.po.UserPO;
import cn.ffcs.buffet.service.ProductModuleService;
import cn.ffcs.buffet.service.ShopCartService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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

    @PassToken
    @ApiOperation(value = "获取商品分类列表")
    @GetMapping(path = "/getProductCategoryList")
    public Result getProductCategoryList() {
        List<ProductCategoryDTO> productCategoryDTOList = productModuleService.selectProductCategoryList();
        return Result.success(productCategoryDTOList);
    }

    @PassToken
    @ApiOperation(value = "根据商品分类id获取商品列表")
    @GetMapping(path = "/getProductListByProductCategoryId")
    public Result getProductListByProductCategoryId(Page<ProductDTO> page, Integer productCategoryId, HttpServletRequest request) {
        Object user = request.getSession().getAttribute("user");
        UserDTO userIdAndUserTelOfToken = TokenUtil.getUserIdAndUserTelOfToken();

        PageInfo<ProductDTO> productDTOList = productModuleService.selectProductListByProductCategoryId(page, productCategoryId);
        if(productDTOList.getTotal() > 0){

            List<ProductDTO> list = productDTOList.getList();

            if (userIdAndUserTelOfToken != null){   //能获取到用户信息，就设置商品加入购物车的数量
                Integer userId = userIdAndUserTelOfToken.getUserId();
                List<ShopCartDetailDTO> shopCartDetailDTOList = (List<ShopCartDetailDTO>)shopCartService.listShopCartByUserId(userId).getData();
                list = setProductNumOfCart(list, shopCartDetailDTOList);
            }

            page.setList(list);
            page.setTotal(productDTOList.getTotal());
            return Result.success(page);
        }
        return Result.success();
    }

    @PassToken
    @ApiOperation(value = "根据商品名称查询商品")
    @GetMapping(path = "/getProductListByProductName")
    public Result getProductListByProductName(Page<ProductDTO> page, String productName, HttpServletRequest request){
        Object user = request.getSession().getAttribute("user");
        UserDTO userIdAndUserTelOfToken = TokenUtil.getUserIdAndUserTelOfToken();

        PageInfo<ProductDTO> productDTOList = productModuleService.selectProductListByProductName(page, productName);
        if(productDTOList.getTotal() > 0){
            List<ProductDTO> list = productDTOList.getList();

            if (userIdAndUserTelOfToken != null){   //能获取到用户信息，就设置商品加入购物车的数量
                Integer userId = userIdAndUserTelOfToken.getUserId();
                List<ShopCartDetailDTO> shopCartDetailDTOList = (List<ShopCartDetailDTO>)shopCartService.listShopCartByUserId(userId).getData();
                list = setProductNumOfCart(list, shopCartDetailDTOList);
            }

            page.setList(list);
            page.setTotal(productDTOList.getTotal());
            return Result.success(page);
        }
        return Result.success();
    }

    @PassToken
    @ApiOperation(value = "获取全部商品")
    @GetMapping(path = "/getAllProductList")
    public Result getAllProductList(Page<ProductDTO> page, HttpServletRequest request){
        Object user = request.getSession().getAttribute("user");
        UserDTO userIdAndUserTelOfToken = TokenUtil.getUserIdAndUserTelOfToken();

        PageInfo<ProductDTO> productDTOList = productModuleService.selectAllProductList(page);
        if(productDTOList.getTotal() > 0){
            List<ProductDTO> list = productDTOList.getList();

            if (userIdAndUserTelOfToken != null){   //能获取到用户信息，就设置商品加入购物车的数量
                Integer userId = userIdAndUserTelOfToken.getUserId();
                List<ShopCartDetailDTO> shopCartDetailDTOList = (List<ShopCartDetailDTO>)shopCartService.listShopCartByUserId(userId).getData();
                list = setProductNumOfCart(list, shopCartDetailDTOList);
            }

            page.setList(list);
            page.setTotal(productDTOList.getTotal());
            return Result.success(page);
        }
        return Result.success();
    }

    @PassToken
    @ApiOperation(value = "根据商品id获取商品属性及其属性值")
    @GetMapping(path = "/getProductAttributeListByProductId/{productId}")
    public Result getProductAttributeListByProductId(@PathVariable Integer productId){
        List<ProductAttributeDTO> productAttributeDTOList = productModuleService.selectProductAttributeByProductId(productId);
        return Result.success(productAttributeDTOList);
    }

    @PassToken
    @ApiOperation(value = "根据商品id和商品规格获取该商品的详细信息")
    @GetMapping(path = "/getSpecificationByProductIdAndSpecification")
    public Result getSpecificationByProductIdAndSpecification(@RequestParam(name = "productId") Integer productId, @RequestParam(name = "productSpecification") String productSpecification){
        ProductSpecificationDTO productSpecificationDTO = productModuleService.selectSpecificationByProductIdAndSpecification(productId, productSpecification);
        return Result.success(productSpecificationDTO);
    }

    @PassToken
    @ApiOperation(value = "根据商品id获取商品详细信息以及商品评价")
    @GetMapping(path = "/getProductDetailAndCommentList/{productId}")
    public Result getProductDetailAndCommentList(@PathVariable Integer productId){
        List<ProductDTO> productDTOList = productModuleService.selectProductDetailAndCommentList(productId);
        return Result.success(productDTOList);
    }

    /**
     * 设置商品加入购物车的数量
     * @param list
     * @param shopCartDetailDTOList
     * @return
     */
    public List<ProductDTO> setProductNumOfCart(List<ProductDTO> list, List<ShopCartDetailDTO> shopCartDetailDTOList){
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
        return list;
    }
}