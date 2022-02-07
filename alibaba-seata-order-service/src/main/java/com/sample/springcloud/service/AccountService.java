package com.sample.springcloud.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sample.springcloud.entity.CommonResult;

@FeignClient(name = "cloud-account-service")
public interface AccountService {

    @PostMapping("account/debit")
    CommonResult<Void> debit(@RequestParam("userId") Long userId, @RequestParam("money") Integer money);

}
