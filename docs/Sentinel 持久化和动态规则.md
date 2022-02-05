# Sentinel 持久化和动态规则

[官网Spring Cloud Alibaba Sentinel动态数据源支持](https://github.com/alibaba/spring-cloud-alibaba/wiki/Sentinel#动态数据源支持)
[官网Sentinel动态规则扩展](https://github.com/alibaba/Sentinel/wiki/动态规则扩展)

# 1 环境
1) 64 bit Centos 7
2) 64 bit JDK 11
3) Spring Cloud Alibaba Hoxton.SR12
4) Sentinel 1.8.1
5) Nacos 2.0.3

# 2 客户端集成动态数据源
1) pom.xml
```
<dependencies>
    <dependency>
        <groupId>com.alibaba.cloud</groupId>
        <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
    </dependency>
    <dependency>
        <groupId>com.alibaba.cloud</groupId>
        <artifactId>spring-cloud-starter-alibaba-sentinel</artifactId>
    </dependency>
    <!-- 动态数据源支持，需要单独引入，用于Sentinel配置持久化进Nacos -->
    <dependency>
        <groupId>com.alibaba.csp</groupId>
        <artifactId>sentinel-datasource-nacos</artifactId>
    </dependency>
</dependencies>
```

2) application.yml
```
spring:
    application:
        name: cloud-sentinel-client
    cloud:
        nacos:
            discovery:
                server-addr: 192.168.1.7:8800
        sentinel:
            # Sentinel控制台配置
            transport:
                # 控制台地址，默认8080端口，可通过启动参数更改（java -Dserver.port=8080 -Dcsp.sentinel.dashboard.server=localhost:8080 -Dproject.name=sentinel-dashboard -jar sentinel-dashboard.jar）
                dashboard: 192.168.1.7:8080
                # 此端口配置会在应用的机器上启动一个Http Server与控制台交互，默认8719端口，如被占用会自动递增寻找可用端口
                # port: 8719
            # 动态数据源
            datasource:
                ds1: # 名称随意
                    nacos: # 数据源类型，支持nacos、redis、zk等
                        server-addr: 192.168.1.7:8800 # Nacos Server地址
                        data-id: ${spring.application.name}-flow # 配置项自定义DataId，注意没有扩展名
                        group-id: SENTINEL_GROUP # 配置项自定义Group
                        data-type: json # 配置项数据格式
                        rule-type: flow # 该数据源中的规则类型：flow、degrade、authority、system、param-flow、gw-flow、gw-api-group
                ds2:
                    nacos:
                        server-addr: 192.168.1.7:8800
                        data-id: ${spring.application.name}-degrade
                        group-id: SENTINEL_GROUP
                        data-type: json
                        rule-type: degrade
```

# 3 规则配置
在Nacos Config中，根据客户端的Group、DataId、data-type创建配置项，写入配置内容规则。本例应用对应配置如下：
```
Group: SENTINEL_GROUP
DataId: cloud-sentinel-client-flow
数据格式：json
配置内容：[flow类型规则...]

Group: SENTINEL_GROUP
DataId: cloud-sentinel-client-degrade
数据格式：json
配置内容：[degrade类型规则...]
```

flow类型规则配置内容示例：
```
[
    {
        "resource": "testRateLimit", // 资源名称
        "limitApp": "default",       // 来源应用
        "grade": 1,                  // 阈值类型：0-线程数，1-QPS
        "count": 1,                  // 单机阈值
        "strategy": 0,               // 流控模式：0-直接，1-关联，2-链路
        "controlBehavior": 0,        // 流控效果：0-快速失败，1-预热，2-排队等待
        "clusterMode": false         // 是否集群
    },
    ...
]
```