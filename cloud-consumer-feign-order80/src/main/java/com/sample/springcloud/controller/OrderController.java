package com.sample.springcloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sample.springcloud.entity.CommonResult;
import com.sample.springcloud.entity.Payment;
import com.sample.springcloud.service.PaymentService;

@RestController
public class OrderController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/consumer/payment/get/{id}")
    public CommonResult<?> getPayment(@PathVariable Long id) {
        return paymentService.getById(id);
    }

    @PostMapping("/consumer/payment/create")
    public CommonResult<?> createPayment(Payment payment) {
        return paymentService.create(payment);
    }

}
