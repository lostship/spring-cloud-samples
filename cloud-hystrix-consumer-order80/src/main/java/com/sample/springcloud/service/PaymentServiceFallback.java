package com.sample.springcloud.service;

import org.springframework.stereotype.Component;

import com.sample.springcloud.entity.CommonResult;

@Component
public class PaymentServiceFallback implements PaymentService {

    @Override
    public CommonResult<String> test() {
        return CommonResult.fail("fallback");
    }

    @Override
    public CommonResult<String> fallback() {
        return CommonResult.fail("fallback");
    }

    @Override
    public CommonResult<String> circuitBreaker(boolean state) {
        return CommonResult.fail("circuit breaker");
    }

}
