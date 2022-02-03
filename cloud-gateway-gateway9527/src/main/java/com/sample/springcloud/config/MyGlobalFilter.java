package com.sample.springcloud.config;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * Gateway三个核心组件：Route、Predicate、Filter
 * 
 * Filter分为：GlobalFilter、GatewayFilter，框架自带了很多默认实现，也可以自定义
 */
@Component
@Slf4j
public class MyGlobalFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("收到请求");

        // String token = exchange.getRequest().getQueryParams().getFirst("token");
        // if (token == null) {
        // log.debug("非法请求");
        // exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        // return exchange.getResponse().setComplete();
        // }

        Mono<Void> mono = chain.filter(exchange);

        log.info("返回响应");

        return mono;
    }

    @Override
    public int getOrder() {
        return 0;
    }

}
