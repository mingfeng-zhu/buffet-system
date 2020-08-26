package cn.ffcs.buffet.controller;

import cn.ffcs.buffet.common.annotation.AvoidRepeatableCommit;
import cn.ffcs.buffet.common.annotation.PassToken;
import cn.ffcs.buffet.common.dto.Page;
import cn.ffcs.buffet.common.dto.Result;
import cn.ffcs.buffet.common.util.TokenUtil;
import cn.ffcs.buffet.model.po.OrderPO;
import cn.ffcs.buffet.service.OrderService;
import cn.ffcs.buffet.service.ShopCartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation(value = "获取当前用户的购物车列表")
    @GetMapping(path = "/listShopCartByUserId")
    public Result listShopCartByUserId() {
        Integer userId = TokenUtil.getUserIdAndUserTelOfToken().getUserId();
        return shopCartService.listShopCartByUserId(userId);
    }

    @AvoidRepeatableCommit
    @ApiOperation(value = "商品加入购物车，productSpecificationId(商品规格id)、goodCount(商品数量)。当商品数量参数为0时，默认将该商品从购物车删除")
    @PostMapping(path = "/addShopCartRecord")
    @ResponseBody
    public Result addShopCartRecord(Integer productSpecificationId, Integer goodCount) {
        Integer userId = TokenUtil.getUserIdAndUserTelOfToken().getUserId();
        return shopCartService.addShopCartRecord(userId, productSpecificationId, goodCount);
    }

}
