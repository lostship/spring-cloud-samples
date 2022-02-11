# 测试环境

## 服务器
CentOS 7.9 64位
JDK 11
MySQL 5.7
Docker 1.13
Nginx 1.18
RabbitMQ 3-management
Redis 6.2
Zookeeper 3.7.0

## MySQL数据库
```
CREATE DATABASE `spring_cloud_samples` DEFAULT CHARACTER SET utf8;
USE `spring_cloud_samples`;

CREATE TABLE `payment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `serial` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
);
CREATE TABLE `storage` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `commodity_code` varchar(255) DEFAULT NULL,
  `count` bigint(20) DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `commodity_code` (`commodity_code`)
);
CREATE TABLE `order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL,
  `commodity_code` varchar(255) DEFAULT NULL,
  `count` int(11) DEFAULT '0',
  `money` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
);
CREATE TABLE `account` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL,
  `money` int(11) DEFAULT '0',
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