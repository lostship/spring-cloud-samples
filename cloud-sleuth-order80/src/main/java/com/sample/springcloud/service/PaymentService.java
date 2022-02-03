package com.sample.springcloud.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.sample.springcloud.entity.CommonResult;

@FeignClient(name = "cloud-provider-payment", path = "payment")
public interface PaymentService {

    @GetMapping("test")
    CommonResult<Void> test();

}
