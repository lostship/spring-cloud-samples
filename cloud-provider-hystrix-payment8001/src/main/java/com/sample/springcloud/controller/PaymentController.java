package com.sample.springcloud.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.sample.springcloud.entity.CommonResult;
import com.sample.springcloud.service.CircuitBreakerTestService;
import com.sample.springcloud.service.FallbackTestService;

@RestController
public class PaymentController {

    @Value("${server.port}")
    private String port;

    @Autowired
    private FallbackTestService fallbackTestService;
    @Autowired
    private CircuitBreakerTestService circuitBreakerTestService;

    @GetMapping("payment/test")
    public CommonResult<?> test() {
        return CommonResult.ok().setData("springcloud with hystrix: " + port + "\t" + UUID.randomUUID());
    }

    @GetMapping("payment/test/fallback")
    public CommonResult<?> timeout() {
        return CommonResult.ok().setData(
                "springcloud with hystrix: " + port + "\t" + fallbackTestService.longMethod(port) + "\t"
                        + UUID.randomUUID());
    }

    @GetMapping("payment/test/defaultfallback1")
    public CommonResult<?> defaultFallback1() {
        fallbackTestService.other1(port);
        return CommonResult.ok().setData("springcloud with hystrix: " + port + "\t" + UUID.randomUUID());
    }

    @GetMapping("payment/test/defaultfallback2")
    public CommonResult<?> defaultFallback2() {
        fallbackTestService.other2();
        return CommonResult.ok().setData("springcloud with hystrix: " + port + "\t" + UUID.randomUUID());
    }

    @GetMapping("payment/test/circuitbreaker/{state}")
    public CommonResult<?> circuitBreaker(@PathVariable boolean state) {
        return CommonResult.ok().setData("springcloud with hystrix: " + port + "\t"
                + circuitBreakerTestService.method(state) + "\t" + UUID.randomUUID());
    }

}
