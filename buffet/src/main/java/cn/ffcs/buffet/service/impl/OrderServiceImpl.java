package cn.ffcs.buffet.service.impl;

import cn.ffcs.buffet.common.dto.Page;
import cn.ffcs.buffet.common.dto.Result;
import cn.ffcs.buffet.common.util.Constant;
import cn.ffcs.buffet.common.util.Snowflake;
import cn.ffcs.buffet.common.util.TokenUtil;
import cn.ffcs.buffet.mapper.OrderMapper;
import cn.ffcs.buffet.model.dto.OrderDetailDTO;
import cn.ffcs.buffet.model.dto.OrderTotalDataDTO;
import cn.ffcs.buffet.model.dto.ProductSpecificationDTO;
import cn.ffcs.buffet.model.po.*;
import cn.ffcs.buffet.service.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service
public class OrderServiceImpl implements OrderService {

    @Autowired(required=false)
    private OrderMapper orderMapper;

    @Autowired(required=false)
    private OrderDetailService orderDetailService;

    @Autowired(required=false)
    private OrderStatusService orderStatusService;

    @Autowired(required=false)
    private ProductModuleService productModuleService;

    @Autowired(required=false)
    private ShopCartService shopCartService;

    @Override
    public Result listOrder(Integer userId, String orderStatus, Page<OrderPO> page, String orderId) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        List<OrderPO> orderPOList = orderMapper.listOrder(userId, orderStatus, orderId);
        PageInfo<OrderPO> pageInfo = new PageInfo<>(orderPOList);

        if (pageInfo.getTotal() > 0) {
            page.setList(pageInfo.getList());
            page.setTotal(pageInfo.getTotal());
            return Result.success(page);
        }
        return Result.success(null);
    }

    @Override
    public Result deleteOrderByOrderId(List<Integer> list) {
        orderMapper.deleteOrderByOrderId(list);
        return Result.success();
    }

    @Override
    public Result updateOrderByIdList(List<Integer> list, String orderStatus) {
        orderMapper.updateOrderByIdList(list, orderStatus);
        return Result.success();
    }

    @Override
    public Result getTotalNumberAndMoney() {
        OrderTotalDataDTO orderTotalDataDTO = new OrderTotalDataDTO();
        int totalNumber = orderMapper.getTotalNumber();
        BigDecimal totalMoney = orderMapper.getTotalMoney();
        if(totalMoney == null) {
            totalMoney = new BigDecimal(0);
        }
        orderTotalDataDTO.setTotalNumber(totalNumber);
        orderTotalDataDTO.setTotalMoney(totalMoney);
        return Result.success(orderTotalDataDTO);
    }

    @Override
    public Result addOrder(Integer[] idList, BigDecimal totalMoney, BigDecimal[] totalMoneyList, Integer addressId, Integer[] goodCountList) {
        //生成订单号
        OrderPO order = new OrderPO();
        //雪花算法，生成订单流水号
        long snowOrderId = Snowflake.getOrderId();
        String orderId = "DD" + snowOrderId;
        order.setOrderId(orderId);
        order.setTotalMoney(totalMoney);
        order.setAddressId(addressId);
        //获取当前用户id
        Integer userId = TokenUtil.getUserIdAndUserTelOfToken().getUserId();
        order.setUserId(userId);
        order.setOrderStatus(Constant.Order_STATUS.wait_pay.getIndex());

        //为避免在for循环中操作数据库，所以应该先批量查出所有商品规格的数据，再进行库存的判断
        List<Integer> list = Arrays.asList(idList);
        List<ProductSpecificationDTO> productList = productModuleService.selectSpecificationByProductSpecificationIdList(list);

        List<OrderDetail> orderDetailList = new ArrayList<>();
        //对订单所有商品的库存进行检查
        for(int num = 0 ;num < idList.length; num++) {
            ProductSpecificationDTO product = productList.get(num);
            int newCount = product.getProductStorage() - goodCountList[num];
            //若是存在商品库存不足的商品
            if(newCount < Constant.PRODUCT_NUMBER_ZERO) {
                return Result.fail("购物车中"+ product.getProductPO().getProductName() +"库存不足！");
            }
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setMoney(totalMoneyList[num]);
            orderDetail.setGoodCount(goodCountList[num]);
            orderDetail.setGoodId(idList[num]);
            orderDetailList.add(orderDetail);
        }

        //新增订单数据
        int result = orderMapper.insertSelective(order);
        if(result == Constant.RETURN_DATA_COUNT) {
            return Result.fail("生成订单失败，请稍后再试");
        }
        for(int count = 0 ;count < orderDetailList.size(); count++) {
            orderDetailList.get(count).setOrderId(order.getId());
        }
        //插入详单数据
        int detailResult = orderDetailService.insertList(orderDetailList);

        //若是详单插入成功，则将用户的购物车清空
        if(detailResult > Constant.RETURN_DATA_COUNT) {
            int deleteShopCart = shopCartService.deleteShopCartByUserId(1);
        }
        //插入一条订单状态记录
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderId(order.getId());
        orderStatus.setStatus(Constant.Order_STATUS.wait_pay.getIndex());
        int orderStatusResult = orderStatusService.insertOrderStatus(orderStatus);
        return Result.success(orderId);
    }

    @Override
    public Result editOrderStatus(Long id, String orderStatus) {
        int result = orderMapper.editOrderStatus(id, orderStatus);
        //订单状态改变记录
        OrderStatus orderStatusPO = new OrderStatus();
        orderStatusPO.setStatus(orderStatus);
        orderStatusPO.setOrderId(id);
        int res = orderStatusService.insertOrderStatus(orderStatusPO);
        if(res > Constant.RETURN_DATA_COUNT) {
            return Result.success();
        }
        return Result.fail("修改状态失败，请稍后再试");
    }

    @Override
    public Result cancelOrder(Long id) {
        return null;
    }

    @Override
    public Result payOrder(Long id, @RequestParam(required = false, value = "idList[]") Integer[] idList,
                           @RequestParam(required = false, value = "goodCountList[]") Integer[] goodCountList) {
        //先二次检查库存是否充足
        //为避免在for循环中操作数据库，所以应该先批量查出所有商品规格的数据，再进行库存的判断
        List<Integer> list = Arrays.asList(idList);
        List<ProductSpecificationDTO> productList = productModuleService.selectSpecificationByProductSpecificationIdList(list);

        //对订单所有商品的库存进行检查
        for(int num = 0 ;num < idList.length; num++) {
            ProductSpecificationDTO product = productList.get(num);
            int newCount = product.getProductStorage() - goodCountList[num];
            //若是存在商品库存不足的商品
            if(newCount < Constant.PRODUCT_NUMBER_ZERO) {
                return Result.fail("购物车中"+ product.getProductPO().getProductName() +"库存不足！");
            }
        }

        //进行商品库存与销量的更新
        List<Integer> numberList = Arrays.asList(goodCountList);
        int result = productModuleService.updateProductStorage(list, numberList);
        //更新成功，则进行订单的支付状态改变,改成待接单
        if(result > Constant.RETURN_DATA_COUNT) {
            int orderResult = orderMapper.editOrderStatus(id, Constant.Order_STATUS.wait_receive.getIndex());
            //修改状态成功，则进行订单状态改变的记录
            if(orderResult > Constant.RETURN_DATA_COUNT) {
                OrderStatus orderStatus = new OrderStatus();
                orderStatus.setOrderId(id);
                orderStatus.setStatus(Constant.Order_STATUS.wait_receive.getIndex());
                orderStatusService.insertOrderStatus(orderStatus);
                return Result.success();
            }
        }
        return Result.fail("支付失败，请稍后再试");
    }

    @Override
    public Result listOrderByCurrentId(Integer userId, Page<OrderPO> page) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        List<OrderPO> orderPOList = orderMapper.listOrderByCurrentId(userId);
        PageInfo<OrderPO> pageInfo = new PageInfo<>(orderPOList);

        if (pageInfo.getTotal() > 0) {
            List<Long> idList = new ArrayList<>();
            for(int count = 0; count < orderPOList.size(); count++) {
                idList.add(orderPOList.get(count).getId());
            }
//            //批量查询订单下的详单信息
//            List<OrderDetail> orderDetailList = orderDetailService.listOrderDetailByOrderIdList(idList);
//
//            List<OrderDetailDTO> orderDetailDTOList = new ArrayList<>();
//            for(int count = 0; count < orderPOList.size(); count++) {
//                OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
//                orderDetailDTO.setOrderPO(orderPOList.get(count));
//                List<OrderDetail> orderDetailList1 = new ArrayList<>();
//                for(int num = 0; num < orderDetailList.size(); num++) {
//                    if(orderDetailList.get(count).getOrderId() == orderPOList.get(num).getId()) {
//                        orderDetailList1.add(orderDetailList.get(num));
//                    }
//                }
//                orderDetailDTO.setOrderDetailList(orderDetailList1);
//                orderDetailDTOList.add(orderDetailDTO);
//            }
            page.setList(pageInfo.getList());
            page.setTotal(pageInfo.getTotal());
            return Result.success(page);
        }
        return Result.success(null);
    }
}
