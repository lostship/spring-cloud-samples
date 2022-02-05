package com.sample.springcloud.service;

import org.springframework.stereotype.Service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PaymentService {

    /**
     * 如果抛出BlockException（违反Sentinel控制策略），则由blockHandler处理
     * 如果抛出其它异常，则由fallback处理
     */
    @SentinelResource(value = "testHotKey", blockHandler = "testHotKeyBlockHandler", fallback = "testHotKeyFallback")
    public String testHotKey(String p1, String p2) {
        log.info("success");
        return "success";
    }

    public String testHotKeyBlockHandler(String p1, String p2) {
        log.info("my blockHandler");
        return "my blockHandler";
    }

    public String testHotKeyFallback(String p1, String p2, Throwable t) {
        log.info("my fallback");
        return "my fallback";
    }

}
