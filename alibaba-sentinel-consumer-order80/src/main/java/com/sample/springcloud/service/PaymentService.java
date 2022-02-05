package com.sample.springcloud.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.sample.springcloud.entity.CommonResult;

// FIXME 开启sentinel对feign的支持后（feign.sentinel.enable=true）启动报错
@FeignClient(name = "cloud-provider-payment", fallback = PaymentServiceFallback.class)
public interface PaymentService {

    @GetMapping("payment/test")
    CommonResult<String> test();

    @GetMapping("payment/test/not_exist_api")
    CommonResult<String> fallback();

}
