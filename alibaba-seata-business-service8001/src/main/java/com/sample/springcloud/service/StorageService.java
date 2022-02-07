package com.sample.springcloud.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sample.springcloud.entity.CommonResult;

@FeignClient(name = "cloud-storage-service")
public interface StorageService {

    @PostMapping("storage/deduct")
    CommonResult<Void> deduct(@RequestParam("commodityCode") String commodityCode,
            @RequestParam("count") Integer count);

}
