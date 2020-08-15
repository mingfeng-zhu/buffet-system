package cn.ffcs.buffet.service.impl;

import cn.ffcs.buffet.common.dto.Result;
import cn.ffcs.buffet.common.util.Constant;
import cn.ffcs.buffet.mapper.OrderStatusMapper;
import cn.ffcs.buffet.mapper.ShopCartMapper;
import cn.ffcs.buffet.model.po.ShopCart;
import cn.ffcs.buffet.service.ShopCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopCartServiceImpl implements ShopCartService {

    @Autowired(required=false)
    private ShopCartMapper shopCartMapper;

    @Override
    public Result listShopCartByUserId(Integer userId) {
        List<ShopCart> shopCartList = shopCartMapper.listShopCartByUserId(userId);
        return Result.success(shopCartList);
    }

    @Override
    public Result addShopCartRecord(Integer userId, Integer productId, Integer goodCount) {

        //判断购物车中是否已有这条记录
        ShopCart preShopCart = shopCartMapper.getShopCartById(userId, productId);
        //若是存在该购物车记录，则对其数量进行改变
        if(preShopCart != null) {
            //若是商品数量为0，则直接将
            if(goodCount == Constant.SHOP_CARD_DELETE_ZERO) {

            }
            return Result.fail("购物");
        }
        //若是不存在，则新增一条记录

        ShopCart shopCart = new ShopCart();
        shopCart.setUserId(userId);
        shopCart.setGoodId(productId);
        shopCart.setGoodCount(goodCount);
        shopCart.setPayStatus(Constant.Shop_Cart_STATUS.un_paid.getIndex());
        int result = shopCartMapper.insertSelective(shopCart);
        if(result > Constant.RETURN_DATA_COUNT) {
            return Result.success();
        }else {
            return Result.fail("添加购物车失败，请稍后再试！");
        }
    }

    @Override
    public Integer deleteShopCartByUserId(Integer userId) {
        return shopCartMapper.deleteShopCartByUserId(userId);
    }
}
