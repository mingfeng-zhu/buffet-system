package cn.ffcs.buffet.service;

import cn.ffcs.buffet.common.dto.Result;
import cn.ffcs.buffet.model.po.ShopCart;

import java.util.List;

/**
 * @author huang.zhao
 * @date: 2020/08/05
 **/
public interface ShopCartService {

    /**
     * 查询某个用户的购物车信息（状态为未支付的）
     * @param userId 用户id
     * @return
     */
    Result listShopCartByUserId(Integer userId);

    /**
     * 将商品加入购物车
     * @param userId 用户id
     * @param productSpecificationId 商品规格id
     * @param goodCount 商品数量
     * @return
     */
    Result addShopCartRecord(Integer userId, Integer productSpecificationId, Integer goodCount);

    /**
     * 清空某个用户的购物车数据
     * @param userId 用户id
     * @return
     */
    Integer deleteShopCartByUserId(Integer userId);
}
