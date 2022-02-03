# 测试环境

## MySQL数据库
```
mysql> CREATE DATABASE `spring_cloud_samples` DEFAULT CHARACTER SET utf8;
mysql> USE `spring_cloud_samples`;
mysql> CREATE TABLE `payment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `serial` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
);
```

## 域名
在/etc/hosts文件中添加测试域名：
```
# sample projects:
127.0.0.1   eureka7001.sample.com
127.0.0.1   eureka7002.sample.com
127.0.0.1   eureka7003.sample.com

127.0.0.1   config3344.sample.com
```