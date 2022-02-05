package com.sample.springcloud.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.sample.springcloud.entity.CommonResult;
import com.sample.springcloud.entity.Payment;

@FeignClient(name = "cloud-provider-payment")
public interface PaymentService {

    @GetMapping("payment/get/{id}")
    CommonResult<Payment> getById(@PathVariable("id") Long id);

    @PostMapping("payment/create")
    CommonResult<Payment> create(@RequestBody Payment payment);

}
