package com.sample.springcloud.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sample.springcloud.entity.CommonResult;
import com.sample.springcloud.entity.Order;

@FeignClient(name = "cloud-order-service")
public interface OrderService {

    @PostMapping("order/create")
    CommonResult<Order> create(@RequestParam("userId") Long userId, @RequestParam("commodityCode") String commodityCode,
            @RequestParam("count") Integer count);

}
