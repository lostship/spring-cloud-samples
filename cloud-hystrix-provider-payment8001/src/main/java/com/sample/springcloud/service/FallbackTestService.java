package com.sample.springcloud.service;

import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

import lombok.extern.slf4j.Slf4j;

@Service
@DefaultProperties(defaultFallback = "defaultFallback") // 默认fallback配置
@Slf4j
public class FallbackTestService {

    // 单独设置fallbacl配置
    @HystrixCommand(fallbackMethod = "longMethodFallback", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")
    })
    public String longMethod(String port) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "success";
    }

    public String longMethodFallback(String port, Throwable t) {
        log.error("longMethodFallback");
        log.error("parameter port: " + port);
        log.error(t.toString());
        return "timeout fallback";
    }

    @HystrixCommand
    public String other1(String port) {
        throw new RuntimeException();
    }

    @HystrixCommand
    public String other2() {
        throw new RuntimeException();
    }

    /**
     * 全局fallback的参数列表没有限制，但是返回类型必须和原方法相同，这样看来只能service层统一返回自定义DTO了
     */
    public String defaultFallback(Throwable t) {
        log.error("defaultFallback");
        log.error(t.toString());
        return null;
    }

}
