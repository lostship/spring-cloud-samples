package com.sample.springcloud.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.sample.springcloud.entity.CommonResult;

@FeignClient(name = "cloud-provider-payment")
public interface PaymentService {

    @GetMapping("payment/test")
    CommonResult<Void> test();

}
