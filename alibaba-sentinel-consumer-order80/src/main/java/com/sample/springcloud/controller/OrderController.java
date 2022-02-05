package com.sample.springcloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sample.springcloud.entity.CommonResult;
import com.sample.springcloud.service.PaymentService;

@RestController
public class OrderController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/consumer/payment/test")
    public CommonResult<?> test() {
        return paymentService.test();
    }

    @GetMapping("/consumer/payment/fallback")
    public CommonResult<?> fallback() {
        return paymentService.fallback();
    }

}
