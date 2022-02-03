package com.sample.springcloud.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sample.springcloud.entity.CommonResult;

@RestController
public class PaymentController {

    @GetMapping("payment/test")
    public CommonResult<?> test(@Value("${server.port}") String port) {
        return CommonResult.ok().setData("springcloud with zookeeper: " + port + "\t" + UUID.randomUUID());
    }

}
