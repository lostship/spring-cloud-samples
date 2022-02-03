package com.sample.springcloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sample.springcloud.entity.CommonResult;
import com.sample.springcloud.service.MessageProvider;

@RestController
public class ProviderController {

    @Autowired
    private MessageProvider messageProvider;

    @GetMapping("send")
    public CommonResult<?> send() {
        messageProvider.send();
        return CommonResult.ok();
    }

}
