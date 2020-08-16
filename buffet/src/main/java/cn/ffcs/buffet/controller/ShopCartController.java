package cn.ffcs.buffet.controller;

import cn.ffcs.buffet.common.dto.Page;
import cn.ffcs.buffet.common.dto.Result;
import cn.ffcs.buffet.model.po.OrderPO;
import cn.ffcs.buffet.service.OrderService;
import cn.ffcs.buffet.service.ShopCartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: demo
 * @Author: huang.zhao
 * @Date: 2020/08/05 10:29
 */
@Api(value = "/api/shopCart", tags = "shopCart购物车服务")
@RestController
@RequestMapping(value = "/api/shopCart")
@Validated
public class ShopCartController {

    @Autowired
    ShopCartService shopCartService;


    @ApiOperation(value = "获取某个用户的购物车列表")
    @GetMapping(path = "/listShopCartByUserId")
    public Result listShopCartByUserId() {
        return shopCartService.listShopCartByUserId(1);
    }

    @ApiOperation(value = "商品加入购物车")
    @GetMapping(path = "/addShopCartRecord")
    public Result addShopCartRecord(Integer productId, Integer goodCount) {
        return shopCartService.addShopCartRecord(1, productId, goodCount);
    }

}
