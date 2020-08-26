package cn.ffcs.buffet.service.impl;

import cn.ffcs.buffet.common.dto.Result;
import cn.ffcs.buffet.common.util.Constant;
import cn.ffcs.buffet.mapper.OrderStatusMapper;
import cn.ffcs.buffet.mapper.ShopCartMapper;
import cn.ffcs.buffet.model.dto.ProductSpecificationDTO;
import cn.ffcs.buffet.model.dto.ShopCartDetailDTO;
import cn.ffcs.buffet.model.po.ShopCart;
import cn.ffcs.buffet.service.ProductModuleService;
import cn.ffcs.buffet.service.ShopCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShopCartServiceImpl implements ShopCartService {

    @Autowired(required=false)
    private ShopCartMapper shopCartMapper;

    @Autowired(required=false)
    private ProductModuleService productModuleService;

    @Override
    public Result listShopCartByUserId(Integer userId) {
        //获取购物车信息
        List<ShopCart> shopCartList = shopCartMapper.listShopCartByUserId(userId);
        List<ShopCartDetailDTO> shopCartDetailDTOList = new ArrayList<>();

        if(shopCartList == null || shopCartList.size() == 0) {
            return Result.success(shopCartDetailDTOList);
        }

        List<Integer> idList = new ArrayList<>();
        for(int count = 0; count < shopCartList.size(); count++) {
            idList.add(shopCartList.get(count).getGoodId());
        }
        //获取商品规则信息
        List<ProductSpecificationDTO> productSpecificationDTOList = productModuleService.selectSpecificationByProductSpecificationIdList(idList);

        //组装返回数据集合

        for(int num = 0; num < shopCartList.size(); num++) {
            ShopCartDetailDTO shopCartDetailDTO = new ShopCartDetailDTO();
            shopCartDetailDTO.setShopCart(shopCartList.get(num));
            shopCartDetailDTO.setProductSpecificationDTO(productSpecificationDTOList.get(num));
            shopCartDetailDTOList.add(shopCartDetailDTO);
        }
        return Result.success(shopCartDetailDTOList);
    }

    @Override
    public Result addShopCartRecord(Integer userId, Integer productSpecificationId, Integer goodCount) {

        //判断购物车中是否已有这条记录
        ShopCart preShopCart = shopCartMapper.getShopCartById(userId, productSpecificationId);
        List<Integer> idList = new ArrayList<>();
        //若是存在该购物车记录，则对其数量进行改变
        if(preShopCart != null) {
            //若是商品数量为0，则直接将购物车记录删除
            if(goodCount == Constant.SHOP_CARD_DELETE_ZERO) {
                int deleteResult = shopCartMapper.deleteRecord(userId, productSpecificationId);
            } else {
                idList.add(productSpecificationId);
                List<ProductSpecificationDTO> productSpecificationDTOList = productModuleService.selectSpecificationByProductSpecificationIdList(idList);
                //若是商品库存不足
                if(productSpecificationDTOList.get(0).getProductStorage() < goodCount) {
                    return Result.fail("该商品库存不足，加入购物车失败。");
                }
                ShopCart shopCart = new ShopCart();
                preShopCart.setGoodCount(goodCount);
                int editResult = shopCartMapper.updateByPrimaryKeySelective(preShopCart);
            }
            return Result.success();
        }
        //若是不存在，则新增一条记录。但是得先判断库存是否充足
        idList.add(productSpecificationId);
        List<ProductSpecificationDTO> productSpecificationDTOList = productModuleService.selectSpecificationByProductSpecificationIdList(idList);
        //若是商品库存不足
        if(productSpecificationDTOList.get(0).getProductStorage() < goodCount) {
            return Result.fail("该商品库存不足，加入购物车失败。");
        }
        ShopCart shopCart = new ShopCart();
        shopCart.setUserId(userId);
        shopCart.setGoodId(productSpecificationId);
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
