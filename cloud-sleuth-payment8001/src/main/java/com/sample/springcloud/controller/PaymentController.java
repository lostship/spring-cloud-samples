package com.sample.springcloud.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sample.springcloud.entity.CommonResult;

@RestController
@RequestMapping("payment")
public class PaymentController {

    @GetMapping("test")
    public CommonResult<?> test() {
        return CommonResult.ok();
    }

}
