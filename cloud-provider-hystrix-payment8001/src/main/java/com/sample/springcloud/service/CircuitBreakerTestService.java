package com.sample.springcloud.service;

import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CircuitBreakerTestService {

    /**
     * Netflix has created a library called Hystrix that implements the circuit breaker
     * pattern (https://martinfowler.com/bliki/CircuitBreaker.html). In a microservice
     * architecture, it is common to have multiple layers of service calls, as shown in the following example:
     * 
     * https://raw.githubusercontent.com/spring-cloud/spring-cloud-netflix/main/docs/src/main/asciidoc/images/Hystrix.png
     * 
     * Figure 1. Microservice Graph
     * A service failure in the lower level of services can cause cascading failure all the way up to the user. When
     * calls to a particular service exceed circuitBreaker.requestVolumeThreshold (default: 20 requests) and the failure
     * percentage is greater than circuitBreaker.errorThresholdPercentage (default: >50%) in a rolling window defined by
     * metrics.rollingStats.timeInMilliseconds (default: 10 seconds), the circuit opens and the call is not made. In
     * cases of error and an open circuit, a fallback can be provided by the developer.
     * 
     * https://raw.githubusercontent.com/spring-cloud/spring-cloud-netflix/main/docs/src/main/asciidoc/images/HystrixFallback.png
     * 
     * Figure 2. Hystrix fallback prevents cascading failures
     * Having an open circuit stops cascading failures and allows overwhelmed or failing services time to recover. The
     * fallback can be another Hystrix protected call, static data, or a sensible empty value. Fallbacks may be chained
     * so that the first fallback makes some other business call, which in turn falls back to static data.
     */
    @HystrixCommand(fallbackMethod = "methodFallback", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),
            @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "10000"), // 在多长时间内
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "20"), // 接收到至少多少次请求
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"), // 其中超过多少百分比都失败了，断路器就断开
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000") // 等待多长时间之后，进入half-open状态，放过一次请求尝试是否能恢复
    })
    public String method(boolean state) {
        log.debug("circuit breaker");
        if (!state) {
            throw new RuntimeException();
        }
        return "success";
    }

    public String methodFallback(boolean state, Throwable t) {
        log.error("methodFallback");
        log.error("parameter state: " + state);
        log.error(t.toString());
        return "circuit breaker fallback";
    }

}
