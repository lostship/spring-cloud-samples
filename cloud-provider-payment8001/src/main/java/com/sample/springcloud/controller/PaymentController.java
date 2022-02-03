package com.sample.springcloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sample.springcloud.entity.CommonResult;
import com.sample.springcloud.entity.Payment;
import com.sample.springcloud.service.PaymentService;

@RestController
@RequestMapping("payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping("get/{id}")
    public CommonResult<?> getById(@PathVariable Long id) {
        Payment payment = paymentService.getById(id);
        return CommonResult.ok().setData(payment);
    }

    @PostMapping("create")
    public CommonResult<?> create(@RequestBody Payment payment) {
        int result = paymentService.create(payment);
        if (result > 0) {
            return CommonResult.ok().setData(payment);
        } else {
            return CommonResult.fail("插入失败");
        }
    }

}
