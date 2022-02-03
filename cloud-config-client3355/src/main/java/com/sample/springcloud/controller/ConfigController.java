package com.sample.springcloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sample.springcloud.entity.CommonResult;

@RestController
@RefreshScope // 动态刷新配置
public class ConfigController {

    @Value("${config.info.branch:null}")
    private String branch;
    @Value("${config.info.profile:null}")
    private String profile;
    @Value("${config.info.version:null}")
    private String version;

    @GetMapping("info")
    public CommonResult<?> getInfo() {
        return CommonResult.ok().setData(branch + ":" + profile + ":" + version);
    }

}
