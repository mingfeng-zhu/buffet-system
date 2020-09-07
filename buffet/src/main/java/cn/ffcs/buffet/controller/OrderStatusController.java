package cn.ffcs.buffet.controller;

import cn.ffcs.buffet.common.annotation.PassToken;
import cn.ffcs.buffet.common.dto.Page;
import cn.ffcs.buffet.common.dto.Result;
import cn.ffcs.buffet.model.dto.OrderDTO;
import cn.ffcs.buffet.service.OrderService;
import cn.ffcs.buffet.service.OrderStatusService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: orderStatus
 * @Author: huang.zhao
 * @Date: 2020/7/30 16:18
 */
@Api(value = "/api/orderStatus", tags = "orderStatus服务")
@RestController
@RequestMapping(value = "/api/orderStatus")
@Validated
public class OrderStatusController {

    @Autowired
    OrderStatusService orderStatusService;

    /**
     * 获取指定订单的时间线
     * @param orderId 订单id
     * @return
     */
    @PassToken
    @ApiOperation(value = "获取指定订单的时间线")
    @GetMapping(path = "/listOrderStatusRecordByOrderId")
    public Result listOrderStatusRecordByOrderId(Long orderId) {
        return orderStatusService.listOrderStatusRecordByOrderId(orderId);
    }
}
