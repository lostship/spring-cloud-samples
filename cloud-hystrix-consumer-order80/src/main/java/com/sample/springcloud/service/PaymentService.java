package com.sample.springcloud.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.sample.springcloud.entity.CommonResult;

/**
 * Hystrix和Feign结合使用，在配置文件中开启Feign对Hystrix的支持
 */
@FeignClient(value = "cloud-provider-payment", fallback = PaymentServiceFallback.class)
public interface PaymentService {

    @GetMapping("payment/test")
    CommonResult<String> test();

    @GetMapping("payment/test/fallback")
    CommonResult<String> fallback();

    @GetMapping("payment/test/circuitbreaker/{state}")
    CommonResult<String> circuitBreaker(@PathVariable("state") boolean state);

}
