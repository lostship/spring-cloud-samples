package com.sample.springcloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sample.springcloud.entity.CommonResult;
import com.sample.springcloud.entity.Order;
import com.sample.springcloud.service.OrderService;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("order/create")
    public CommonResult<?> create(Long userId, String commodityCode, Integer count) {
        Order order = orderService.create(userId, commodityCode, count);
        return CommonResult.ok().setData(order);
    }

}
