package com.sample.springcloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sample.springcloud.entity.CommonResult;
import com.sample.springcloud.service.StorageService;

@RestController
public class StorageController {

    @Autowired
    private StorageService storageService;

    @PostMapping("storage/deduct")
    public CommonResult<?> deduct(String commodityCode, Integer count) {
        storageService.deduct(commodityCode, count);
        return CommonResult.ok();
    }

}
