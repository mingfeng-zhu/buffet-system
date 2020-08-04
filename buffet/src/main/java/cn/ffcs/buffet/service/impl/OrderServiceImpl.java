package cn.ffcs.buffet.service.impl;

import cn.ffcs.buffet.common.dto.Page;
import cn.ffcs.buffet.common.dto.Result;
import cn.ffcs.buffet.mapper.OrderMapper;
import cn.ffcs.buffet.model.dto.OrderTotalDataDTO;
import cn.ffcs.buffet.model.po.CategoryPO;
import cn.ffcs.buffet.model.po.OrderPO;
import cn.ffcs.buffet.service.OrderService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;


@Service
public class OrderServiceImpl implements OrderService {

    @Autowired(required=false)
    private OrderMapper orderMapper;

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
}
