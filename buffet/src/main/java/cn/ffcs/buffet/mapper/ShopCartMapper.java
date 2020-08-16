package cn.ffcs.buffet.mapper;

import cn.ffcs.buffet.model.po.ShopCart;

import java.util.List;

public interface ShopCartMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ShopCart record);

    int insertSelective(ShopCart record);

    ShopCart selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ShopCart record);

    int updateByPrimaryKey(ShopCart record);

    List<ShopCart> listShopCartByUserId(Integer userId);

    ShopCart getShopCartById(Integer userId, Integer productId);

    Integer deleteShopCartByUserId(Integer userId);
}