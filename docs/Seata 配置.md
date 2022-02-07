# Seata 配置

# 1 环境
1) 64 bit Centos 7
2) 64 bit JDK 11
3) Spring Cloud Alibaba Hoxton.SR12
4) Seata 1.3.0
5) Nacos 2.0.3

# 2 配置中心
[config-center](https://github.com/seata/seata/tree/develop/script/config-center)

Seata支持Nacos、Redis、Zookeeper等配置中心，本例使用Nacos。下载初始配置示例和配置中心初始化脚本，按照官网相同目录结构存放：
- config-center/config.txt
- config-center/nacos/nacos-config.sh

执行初始化脚本，注意Group、Namespace等参数需要和Seata服务端、客户端的Nacos信息配置一致：
```
$ sh ./nacos-config.sh -h localhost -p 8848 -g SEATA_GROUP -t 5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca -u username -w password

Parameter Description:

-h: host, the default value is localhost.

-p: port, the default value is 8848.

-g: Configure grouping, the default value is 'SEATA_GROUP'.

-t: Tenant information, corresponding to the namespace ID field of Nacos, the default value is ''.

-u: username, nacos 1.2.0+ on permission control, the default value is ''.

-w: password, nacos 1.2.0+ on permission control, the default value is ''.
```

初始化配置会被上传到Nacos Config配置中心，注意其中的配置项值需要根据实际情况新建或修改：
```
# config.txt中的默认配置
service.vgroupMapping.my_test_tx_group=default

# 创建自己的事务分组-集群映射配置项
service.vgroupMapping.<txGroup>=<clusterName>

# 数据库连接信息等...
```

# 2 服务端（Seata Server）
[server](https://github.com/seata/seata/tree/develop/script/server)

1) 服务端数据库
Seata TC需要使用数据库进行全局和分支事务管理，创建服务端数据库，并从官网下载导入初始化脚本：
```
mysql> create database `seata` default character set utf8;

mysql> -- 导入Server端数据库初始化脚本
```

2) 配置文件
服务端主要包含两个配置文件：
- seata/config/registry.conf：服务注册和配置中心相关配置，`registry.type`和`config.type`默认值都是`"file"`，可以修改为`nacos`。
- seata/config/file.conf：Seata全部配置项都在这里，如果registry.conf配置文件中的`config.type="file"`，则全部配置从本地file.conf配置文件中加载；如果`config.type`使用配置中心，则默认配置从配置中心加载，但是可以通过本地file.conf配置文件中的配置项来覆盖配置中心的对应配置项，进行定制化配置。

主要修改：
- registry.conf中的服务注册和配置中心从`"file"`修改为`"nacos"`，并配置Nacos相关信息
- 配置中心根据实际情况修改`store.mode=db`，以及seata服务端数据库连接相关信息

3) 启动Seata Server端
```
$ cd seata/bin
$ ./seata-server.sh
```

# 3 客户端（微服务应用）
1) 数据库
Seata客户端需要使用数据库进行事务回滚日志记录等管理，在所有微服务应用连接的业务数据库，根据所需事务模式导入官网下载的数据表初始化脚本：
- AT模式所需客户端数据表
- Saga模式所需客户端数据表

2) 配置文件
可以在resources资源目录下拷贝registry.conf和file.conf配置文件进行配置管理。

本例不使用独立文件，而是在application.yml中进行配置：
```
seata:
  # enabled: true
  # application-id: ${spring.application.name}
  tx-service-group: sample-seata-service-group # 自定义的事务分组名称
  # enable-auto-data-source-proxy: true
  # use-jdk-proxy: false
  # excludes-for-auto-proxying: firstClassNameForExclude,secondClassNameForExclude
  service:
    vgroup-mapping:
      sample-seata-service-group: default # 自定义的事务分组-集群映射，集群默认是default，在配置中心中要有相同的映射配置项
    # enable-degrade: false
    # disable-global-transaction: false
  registry:
    type: nacos
    nacos:
      server-addr: 192.168.1.7:8800
      group : SEATA_GROUP # 注意和服务端以及配置中心保持一致
      application: seata-server # 默认Seata服务端注册名称是seata-server
      # namespace:  # 注意和服务端以及配置中心保持一致
      # username: ""
      # password: ""
  config:
    type: nacos
    nacos:
      serverAddr: 192.168.1.7:8800
      group: SEATA_GROUP  # 注意和服务端以及配置中心保持一致
      # namespace:  # 注意和服务端以及配置中心保持一致
      # username: ""
      # password: ""
# 使用配置中心后默认配置从配置中心加载，也可以在此进行定制化配置
#  client:
#    rm:
#      async-commit-buffer-limit: 1000
#      report-retry-count: 5
#      table-meta-check-enable: false
#      report-success-enable: false
#      saga-branch-register-enable: false
#      lock:
#        retry-interval: 10
#        retry-times: 30
#        retry-policy-branch-rollback-on-conflict: true
#    tm:
#      degrade-check: false
#      degrade-check-period: 2000
#      degrade-check-allow-times: 10
#      commit-retry-count: 5
#      rollback-retry-count: 5
#    undo:
#      data-validation: true
#      log-serialization: jackson
#      log-table: undo_log
#      only-care-update-columns: true
#    log:
#      exceptionRate: 100
#  transport:
#    shutdown:
#      wait: 3
#    thread-factory:
#      boss-thread-prefix: NettyBoss
#      worker-thread-prefix: NettyServerNIOWorker
#      server-executor-thread-prefix: NettyServerBizHandler
#      share-boss-worker: false
#      client-selector-thread-prefix: NettyClientSelector
#      client-selector-thread-size: 1
#      client-worker-thread-prefix: NettyClientWorkerThread
#      worker-thread-size: default
#      boss-thread-size: 1
#    type: TCP
#    server: NIO
#    heartbeat: true
#    serialization: seata
#    compressor: none
#    enable-client-batch-send-request: true

# 设置Feign连接和响应超时时长，默认超时时间过短（1s）
feign:
  client:
    config:
      default:
        connect-timeout: 6000
        read-timeout: 6000
```

3) 编码
在业务Service层方法上使用`@GlobalTransactional`注解声明开启全局事务。