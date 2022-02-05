package com.sample.springcloud.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.sample.springcloud.config.GlobalBlockHandler;
import com.sample.springcloud.entity.CommonResult;
import com.sample.springcloud.service.PaymentService;

@RestController
@RequestMapping("payment")
public class PaymentController {

    @Value("${server.port}")
    private String port;

    @Autowired
    public PaymentService paymentService;

    @GetMapping("test")
    @SentinelResource(value = "testfallback", blockHandlerClass = GlobalBlockHandler.class, blockHandler = "handleCommonResult")
    public CommonResult<?> test() {
        return CommonResult.ok().setData(port + " - " + UUID.randomUUID());
    }

    @GetMapping("test/hotkey")
    public CommonResult<?> testHotKey(String p1, String p2) {
        return CommonResult.ok().setData(paymentService.testHotKey(p1, p2));
    }

}
