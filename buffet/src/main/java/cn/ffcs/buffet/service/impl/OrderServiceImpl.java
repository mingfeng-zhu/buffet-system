package cn.ffcs.buffet.service.impl;

import cn.ffcs.buffet.common.dto.Page;
import cn.ffcs.buffet.common.dto.Result;
import cn.ffcs.buffet.common.util.Constant;
import cn.ffcs.buffet.common.util.Snowflake;
import cn.ffcs.buffet.common.util.TokenUtil;
import cn.ffcs.buffet.mapper.OrderMapper;
import cn.ffcs.buffet.mapper.UserPOMapper;
import cn.ffcs.buffet.model.dto.*;
import cn.ffcs.buffet.model.po.*;
import cn.ffcs.buffet.model.vo.OrderDataVO;
import cn.ffcs.buffet.service.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.*;


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

    @Autowired(required=false)
    private UserPOMapper userPOMapper;

    @Override
    public Result listOrder(Integer userId, String orderStatus, Page<OrderDTO> page, String orderId) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        List<OrderDTO> orderPOList = orderMapper.listOrder(userId, orderStatus, orderId);
        PageInfo<OrderDTO> pageInfo = new PageInfo<>(orderPOList);
        //分页数据返回
        page.setList(pageInfo.getList());
        page.setTotal(pageInfo.getTotal());
        return Result.success(page);
    }

    @Override
    public Result deleteOrderByOrderId(List<Integer> list) {
        orderMapper.deleteOrderByOrderId(list);
        return Result.success();
    }

    @Override
    public Result updateOrderByIdList(List<Long> list, String orderStatus) {
        orderMapper.updateOrderByIdList(list, orderStatus);
        //生成订单状态改变记录
        List<OrderStatus> orderStatusList = new ArrayList<>();
        for(Long id: list) {
            OrderStatus orderStatusPO = new OrderStatus();
            orderStatusPO.setOrderId(id);
            orderStatusPO.setStatus(orderStatus);
            orderStatusList.add(orderStatusPO);
        }
        orderStatusService.insertOrderStatusList(orderStatusList);
        return Result.success();
    }

    @Override
    public Result getTotalNumberAndMoney() {
        OrderTotalDataDTO orderTotalDataDTO = new OrderTotalDataDTO();
        //获取总交易数
        int totalNumber = orderMapper.getTotalNumber();
        //获取总交易额
        BigDecimal totalMoney = orderMapper.getTotalMoney();
        if(totalMoney == null) {
            totalMoney = new BigDecimal(0);
        }
        //获取今日的0点的时间戳
        Calendar dateRight = Calendar.getInstance();
        dateRight.add(Calendar.DAY_OF_MONTH, Constant.CALENDAR_ONE);
        dateRight.set(Calendar.HOUR_OF_DAY,  Constant.CALENDAR_ZERO);
        dateRight.set(Calendar.MINUTE, Constant.CALENDAR_ZERO);
        dateRight.set(Calendar.SECOND, Constant.CALENDAR_ZERO);
        //明天凌晨0点
        Date afterDate = dateRight.getTime();
        dateRight.add(Calendar.DAY_OF_MONTH, -Constant.CALENDAR_ONE);
        Date beforeDate = dateRight.getTime();
        //获取今日交易数
        int currentDayNumber = orderMapper.getCurrentDayNumber(beforeDate, afterDate);
        //获取今日交易额
        BigDecimal currentDayMoney = orderMapper.getCurrentDayMoney(beforeDate, afterDate);
        if(currentDayMoney == null) {
            currentDayMoney = new BigDecimal(0);
        }
        orderTotalDataDTO.setTotalNumber(totalNumber);
        orderTotalDataDTO.setTotalMoney(totalMoney);
        orderTotalDataDTO.setCurrentDayMoney(currentDayMoney);
        orderTotalDataDTO.setCurrentDayNumber(currentDayNumber);
        return Result.success(orderTotalDataDTO);
    }

    @Override
    public Result addOrder(Integer[] idList, BigDecimal totalMoney, BigDecimal[] totalMoneyList, Integer addressId, Integer[] goodCountList, Integer[] shopCartIdList) {
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
        List<Integer> shopIdList = Arrays.asList(shopCartIdList);
        if(detailResult > Constant.RETURN_DATA_COUNT) {
            int deleteShopCart = shopCartService.deleteShopCartByIdList(shopIdList);
        }
        //插入一条订单状态记录
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderId(order.getId());
        orderStatus.setStatus(Constant.Order_STATUS.wait_pay.getIndex());
        int orderStatusResult = orderStatusService.insertOrderStatus(orderStatus);
        return Result.success(order.getId());
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
        int orderResult;
        String currentStatus;
        //获取当前订单信息
        OrderPO orderPO = orderMapper.selectByPrimaryKey(id);
        //判断订单现在状态，若是待支付状态，则只需要进行订单状态的改变
        if(Constant.Order_STATUS.wait_pay.getIndex().equals(orderPO.getOrderStatus())) {
            //进行订单的支付状态改变,改成已取消
            orderResult = orderMapper.editOrderStatus(id, Constant.Order_STATUS.cancelled.getIndex());
            currentStatus = Constant.Order_STATUS.cancelled.getIndex();
        }
        //若是处于已支付，即待接单状态，则需要将商品库存回退，再进行取消订单的操作。
        else if(Constant.Order_STATUS.wait_receive.getIndex().equals(orderPO.getOrderStatus())) {
            //获取当前订单下的详单信息
            List<Long> orderIdList = new ArrayList<>();
            orderIdList.add(id);
            List<OrderDetail> orderDetailList = orderDetailService.listOrderDetailByOrderIdList(orderIdList);

            //进行商品库存与销量的更新
            List<Integer> list = new ArrayList<>();
            List<Integer> countList = new ArrayList<>();
            for(int count = 0; count < orderDetailList.size(); count++) {
                list.add(orderDetailList.get(count).getGoodId());
                countList.add(orderDetailList.get(count).getGoodCount());
            }
            int result = productModuleService.updateProductStorage(list, countList);
            //进行订单的支付状态改变,改成已取消
            orderResult = orderMapper.editOrderStatus(id, Constant.Order_STATUS.cancelled.getIndex());
            currentStatus = Constant.Order_STATUS.cancelled.getIndex();

            //进行用户金额的返还
            int moneyResult = userPOMapper.updateMoneyByUser(orderPO.getUserId(), orderPO.getTotalMoney());
        }
        //若是处于其他状态，则需要管理员的审核才能取消订单
        else {
            orderResult = orderMapper.editOrderStatus(id, Constant.Order_STATUS.in_cancel.getIndex());
            currentStatus = Constant.Order_STATUS.in_cancel.getIndex();
        }

        //修改状态成功，则进行订单状态改变的记录
        if(orderResult > Constant.RETURN_DATA_COUNT) {
            OrderStatus orderStatus = new OrderStatus();
            orderStatus.setOrderId(id);
            orderStatus.setStatus(currentStatus);
            orderStatusService.insertOrderStatus(orderStatus);
            return Result.success();
        }


        return null;
    }

    @Override
    public Result payOrder(Long id, @RequestParam(required = false, value = "idList[]") Integer[] idList,
                           @RequestParam(required = false, value = "goodCountList[]") Integer[] goodCountList) {
        //先二次检查库存是否充足
        //为避免在for循环中操作数据库，所以应该先批量查出所有商品规格的数据，再进行库存的判断
        OrderPO orderPO = orderMapper.selectByPrimaryKey(id);
        UserPO userPO = userPOMapper.selectUserByUserId(orderPO.getUserId());
        //若是余额不足
        if(userPO.getTotalMoney().compareTo(orderPO.getTotalMoney()) == -1) {
            return Result.fail("支付失败，余额不足");
        }

        //扣除金额
        int moneyResult = userPOMapper.updateMoneyByUser(orderPO.getUserId(), orderPO.getTotalMoney().negate());
        List<Integer> list = Arrays.asList(idList);
        List<ProductSpecificationDTO> productList = productModuleService.selectSpecificationByProductSpecificationIdList(list);
        List<Integer> countList = new ArrayList<>();
        //对订单所有商品的库存进行检查
        for(int num = 0 ;num < idList.length; num++) {
            ProductSpecificationDTO product = productList.get(num);
            int newCount = product.getProductStorage() - goodCountList[num];
            //若是存在商品库存不足的商品
            if(newCount < Constant.PRODUCT_NUMBER_ZERO) {
                return Result.fail("购物车中"+ product.getProductPO().getProductName() +"库存不足！");
            }
            countList.add(0 - goodCountList[num]);
        }

        //进行商品库存与销量的更新
//        List<Integer> numberList = Arrays.asList(goodCountList);
        int result = productModuleService.updateProductStorage(list, countList);
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
    public Result listOrderByCurrentId(Integer userId, Page<OrderPO> page, String orderStatus) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        List<OrderPO> orderPOList = orderMapper.listOrderByCurrentId(userId, orderStatus);
        PageInfo<OrderPO> pageInfo = new PageInfo<>(orderPOList);

        if (orderPOList.size() > 0) {
            List<Long> idList = new ArrayList<>();
            for(int count = 0; count < orderPOList.size(); count++) {
                idList.add(orderPOList.get(count).getId());
            }
            //批量查询订单下的详单信息
            List<OrderDetail> orderDetailList = orderDetailService.listOrderDetailByOrderIdList(idList);

            List<Integer> productSpecificationIdList = new ArrayList<>();
            for(int i = 0; i < orderDetailList.size(); i++) {
                productSpecificationIdList.add(orderDetailList.get(i).getGoodId());
            }

            List<OrderDetailAndProductDTO> orderDetailAndProductDTOList = new ArrayList<>();
            //查询所有详单的商品规格信息
            List<ProductSpecificationDTO> productSpecificationDTOList = productModuleService.selectSpecificationByProductSpecificationIdList(productSpecificationIdList);
            for(int count = 0; count < orderDetailList.size(); count++) {
                for(int num = 0; num < productSpecificationDTOList.size(); num++) {
                    if(orderDetailList.get(count).getGoodId() == productSpecificationDTOList.get(num).getProductSpecificationId()) {
                        OrderDetailAndProductDTO orderDetailAndProductDTO = new OrderDetailAndProductDTO();
                        orderDetailAndProductDTO.setOrderDetail(orderDetailList.get(count));
                        orderDetailAndProductDTO.setProductSpecificationDTO(productSpecificationDTOList.get(num));
                        orderDetailAndProductDTOList.add(orderDetailAndProductDTO);
                    }
                }
            }

            List<OrderDetailDTO> orderDetailDTOList = new ArrayList<>();
            for(int count = 0; count < orderPOList.size(); count++) {
                OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
                orderDetailDTO.setOrderPO(orderPOList.get(count));
                List<OrderDetailAndProductDTO> orderDetailAndProductDTOList1 = new ArrayList<>();


                for(int num = 0; num < orderDetailAndProductDTOList.size(); num++) {
                    if(orderDetailAndProductDTOList.get(num).getOrderDetail().getOrderId().longValue() == orderPOList.get(count).getId().longValue()) {
                        orderDetailAndProductDTOList1.add(orderDetailAndProductDTOList.get(num));
                    }
                }
                orderDetailDTO.setOrderDetailAndProductDTO(orderDetailAndProductDTOList1);
                orderDetailDTOList.add(orderDetailDTO);
            }
//            page.setList(pageInfo.getList());
//            page.setTotal(pageInfo.getTotal());
            return Result.success(orderDetailDTOList);
        }
        return Result.success(null);
    }

    @Override
    public Result getOrderById(Long id) {
        OrderPO orderPO = orderMapper.selectByPrimaryKey(id);

        List<Long> idList = new ArrayList<>();
        idList.add(orderPO.getId());
        List<OrderDetail> orderDetailList = orderDetailService.listOrderDetailByOrderIdList(idList);

        List<Integer> productSpecificationIdList = new ArrayList<>();
        for(int i = 0; i < orderDetailList.size(); i++) {
            productSpecificationIdList.add(orderDetailList.get(i).getGoodId());
        }

        List<OrderDetailAndProductDTO> orderDetailAndProductDTOList = new ArrayList<>();
        //查询所有详单的商品规格信息
        List<ProductSpecificationDTO> productSpecificationDTOList = productModuleService.selectSpecificationByProductSpecificationIdList(productSpecificationIdList);
        for(int count = 0; count < orderDetailList.size(); count++) {
            for(int num = 0; num < productSpecificationDTOList.size(); num++) {
                if(orderDetailList.get(count).getGoodId() == productSpecificationDTOList.get(num).getProductSpecificationId()) {
                    OrderDetailAndProductDTO orderDetailAndProductDTO = new OrderDetailAndProductDTO();
                    orderDetailAndProductDTO.setOrderDetail(orderDetailList.get(count));
                    orderDetailAndProductDTO.setProductSpecificationDTO(productSpecificationDTOList.get(num));
                    orderDetailAndProductDTOList.add(orderDetailAndProductDTO);
                }
            }
        }

        OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
        orderDetailDTO.setOrderPO(orderPO);
        orderDetailDTO.setOrderDetailAndProductDTO(orderDetailAndProductDTOList);

        return Result.success(orderDetailDTO);
    }

    @Override
    public Result cancelOrderList(List<Long> idList, String orderStatus) {

        //获取当前订单下的详单信息
        List<OrderDetail> orderDetailList = orderDetailService.listOrderDetailByOrderIdList(idList);

        //进行商品库存与销量的更新
        List<Integer> list = new ArrayList<>();
        List<Integer> countList = new ArrayList<>();
        for(int count = 0; count < orderDetailList.size(); count++) {
            list.add(orderDetailList.get(count).getGoodId());
            countList.add(orderDetailList.get(count).getGoodCount());
        }
        int result = productModuleService.updateProductStorage(list, countList);

        //进行用户金额的返还
        for(int count = 0; count < idList.size(); count++) {
            OrderPO orderPO = orderMapper.selectByPrimaryKey(idList.get(count));
            int moneyResult = userPOMapper.updateMoneyByUser(orderPO.getUserId(), orderPO.getTotalMoney());
        }

        //进行订单的支付状态改变,改成已取消
        orderMapper.updateOrderByIdList(idList, Constant.Order_STATUS.cancelled.getIndex());
        //插入订单改变记录
        List<OrderStatus> orderStatusList = new ArrayList<>();
        for(int count = 0; count < idList.size(); count++) {
            OrderStatus orderStatus1 = new OrderStatus();
            orderStatus1.setOrderId(idList.get(count));
            orderStatus1.setStatus(Constant.Order_STATUS.cancelled.getIndex());
            orderStatusList.add(orderStatus1);
        }
        Integer insertResult = orderStatusService.insertOrderStatusList(orderStatusList);
        return Result.success();
    }

    @Override
    public Result getEchartsData(Integer dayCount) {
        Calendar dateRight = Calendar.getInstance();
        dateRight.add(Calendar.DAY_OF_MONTH, Constant.CALENDAR_ONE);
        dateRight.set(Calendar.HOUR_OF_DAY,  Constant.CALENDAR_ZERO);
        dateRight.set(Calendar.MINUTE, Constant.CALENDAR_ZERO);
        dateRight.set(Calendar.SECOND, Constant.CALENDAR_ZERO);
        //明天凌晨0点
        Date dateAfter = dateRight.getTime();
        //六天前凌晨
        dateRight.add(Calendar.DAY_OF_MONTH, -dayCount);
        Date dateBefore = dateRight.getTime();
        //获取最近七天注册的新用户的数据
        List<OrderPO> orders = orderMapper.listOrderByDay(dateBefore, dateAfter);
        List<Integer> countList = new ArrayList<>();
        List<BigDecimal> moneyList = new ArrayList<>();
        for(int num = 0; num < dayCount; num++) {
            countList.add(Constant.CALENDAR_ZERO);
            moneyList.add(new BigDecimal(Constant.CALENDAR_ZERO));
        }
        Calendar dateLeft = Calendar.getInstance();
        dateLeft.setTime(dateAfter);
        dateLeft.add(Calendar.DAY_OF_MONTH, -Constant.CALENDAR_ONE);
        dateRight.setTime(dateAfter);
        Calendar demo = Calendar.getInstance();
        //进行echarts图标数据整理
        for(int i = dayCount - 1; i >= 0; i--) {
            for (OrderPO order : orders) {
                Date orderDate = order.getCreateTime();
                demo.setTime(orderDate);
                if (demo.after(dateLeft) && demo.before(dateRight)) {
                    moneyList.set(i, moneyList.get(i).add(order.getTotalMoney()));
                    countList.set(i, countList.get(i) + Constant.CALENDAR_ONE);
                }
            }
            dateLeft.add(Calendar.DAY_OF_MONTH, -Constant.CALENDAR_ONE);
            dateRight.add(Calendar.DAY_OF_MONTH, -Constant.CALENDAR_ONE);
        }
        //返回VO
        OrderDataVO orderDataVO = new OrderDataVO();
        orderDataVO.setCountList(countList);
        orderDataVO.setMoneyList(moneyList);
        return Result.success(orderDataVO);
    }

}
