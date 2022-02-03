package com.sample.springcloud.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    /**
     * 编码方式配置gateway路由规则
     */
    // @Bean
    // public RouteLocator routeLocator(RouteLocatorBuilder builder) {
    // RouteLocatorBuilder.Builder routes = builder.routes();
    // routes.route("cloud-provider-payment",
    // r -> r.path("/payment/**").uri("lb://cloud-provider-payment"));
    // routes.route("cloud-provider-payment-actuator",
    // r -> r.path("/actuator/**").uri("lb://cloud-provider-payment"));
    // return routes.build();
    // }

}
