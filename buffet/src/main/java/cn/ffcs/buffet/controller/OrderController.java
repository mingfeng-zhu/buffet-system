package cn.ffcs.buffet.controller;

import cn.ffcs.buffet.common.dto.Page;
import cn.ffcs.buffet.common.dto.Result;
import cn.ffcs.buffet.model.po.OrderPO;
import cn.ffcs.buffet.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;

import java.util.Arrays;
import java.util.List;

/**
 * @Description: order
 * @Author: huang.zhao
 * @Date: 2020/7/30 16:18
 */
@Api(value = "/api/order", tags = "order服务")
@RestController
@RequestMapping(value = "/api/order")
@Validated
public class OrderController {

    @Autowired
    OrderService orderService;

    /**
     * 获取某个用户的所有订单信息,分页形式
     * @param userId 用户id
     * @param orderStatus 订单状态
     * @param page 分页数据
     * @param orderId 订单流水号
     * @return
     */
    @ApiOperation(value = "获取订单列表，可选择的条件有用户id、订单流水号、订单状态。查询为分页查询。")
    @GetMapping(path = "/listOrder")
    public Result listOrder(Integer userId, String orderStatus, Page<OrderPO> page, String orderId) {
        return orderService.listOrder(userId, orderStatus, page, orderId);
    }

    /**
     * 删除指定订单id的订单，批量形式
     * @param idList idList
     * @return
     */
    @ApiOperation(value = "删除指定订单id的订单，批量形式")
    @RequestMapping(value = "/deleteOrderByIdList")
    @ResponseBody
    public Result deleteOrderByIdList(@RequestParam(required = false, value = "idList[]") Integer[] idList) {
        if(idList == null || idList.length ==0) {
            return Result.fail("空数组");
        }
        List<Integer> list = java.util.Arrays.asList(idList);
        return orderService.deleteOrderByOrderId(list);
    }

    /**
     * 修改订单状态，批量形式
     * @param idList idList
     * @param orderStatus 订单状态
     * @return
     */
    @ApiOperation(value = "修改订单状态，批量形式(用于后台的管理员批量发货等功能)")
    @RequestMapping(value = "/updateOrderByIdList")
    @ResponseBody
    public Result updateOrderByIdList(@RequestParam(required = false, value = "idList[]") Integer[] idList, String orderStatus) {
        if(idList == null || idList.length ==0) {
            return Result.fail("空数组");
        }
        List<Integer> list = java.util.Arrays.asList(idList);
        return orderService.updateOrderByIdList(list, orderStatus);
    }

    /**
     * 查询订单总数、总交易额
     * @return
     */
    @ApiOperation(value = "查询订单总数、总交易额")
    @RequestMapping(value = "/getTotalNumberAndMoney")
    @ResponseBody
    public Result getTotalNumberAndMoney() {
        return orderService.getTotalNumberAndMoney();
    }

}
