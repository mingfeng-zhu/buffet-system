package cn.ffcs.buffet.service.impl;

import cn.ffcs.buffet.mapper.OrderDetailMapper;
import cn.ffcs.buffet.model.po.OrderDetail;
import cn.ffcs.buffet.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {

    @Autowired(required=false)
    private OrderDetailMapper orderMapper;

    @Override
    public Integer insertList(List<OrderDetail> list) {
        return orderMapper.insertList(list);
    }
}
