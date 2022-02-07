package com.sample.springcloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sample.springcloud.entity.CommonResult;
import com.sample.springcloud.entity.Order;
import com.sample.springcloud.service.BusinessService;

@RestController
public class BusinessController {

    @Autowired
    private BusinessService businessService;

    @PostMapping("business/purchase")
    public CommonResult<?> purchase(Long userId, String commodityCode, Integer count) {
        Order order = businessService.purchase(userId, commodityCode, count);
        return CommonResult.ok().setData(order);
    }

}
