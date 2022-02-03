# Nacos 2.x 集群配置

[官网集群部署说明](https://nacos.io/zh-cn/docs/cluster-mode-quick-start.html)
[官网2.x兼容文档](https://nacos.io/zh-cn/docs/2.0.0-compatibility.html)

# 1 环境
1) 64 bit Centos 7
2) 64 bit JDK 11
3) Nacos 2.0.3
4) Nginx 1.18.0

# 2 Nacos集群配置
```
$ vim /opt/nacos/conf/cluster.conf
# ip:port
192.168.1.11:8848
192.168.1.12:8848
192.168.1.13:8848
```

# 3 使用外置MySQL数据库
1) 初始化数据库
```
mysql> create database nacos_config default character set utf8;
mysql> use nacos_config;
mysql> source /tmp/nacos-mysql.sql; -- https://github.com/alibaba/nacos/blob/master/distribution/conf/nacos-mysql.sql

mysql> create user nacos identified by <password>;
mysql> grant all privileges on nacos_config.* to nacos@'%' identified by <password>;
mysql> flush privileges;
```

2) Nacos数据库配置
```
$ vim /opt/nacos/confapplication.properties
spring.datasource.platform=mysql
db.num=1
db.url.0=jdbc:mysql://<ip>:3306/nacos_config?characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true&useUnicode=true&useSSL=false&serverTimezone=Asia/shanghai
db.user.0=root
db.password.0=123456
```

# 4 Nginx
Nacos 2.x 新增了gRPC通信方式，需要增加两个端口，新增端口是在主端口（server.port，默认8848）基础上，进行一定偏移量自动生成。
|端口|主端口偏移量|描述|
|-|-|-|
|9848|1000|客户端gRPC请求服务端端口，用于客户端向服务端发起连接和请求|
|9849|1001|服务端gRPC请求服务端端口，用于服务间同步等|

因此使用VIP/Nginx请求时，除了客户端http请求服务端主端口的配置之外，还需要增加客户端gRPC请求服务端端口的配置，并且需要配置成TCP转发，不能配置http2转发，否则连接会被Nginx断开。
在安装Nginx时也需要安装stream模块。

1) 安装
```
$ cd /user/local/src/nginx
$ ./configure --prefix=/usr/local/nginx --with-http_stub_status_module --with-http_ssl_module --with-http_v2_module --with-stream
$ make
$ make install
```

2) 配置

```
$ cd /usr/local/nginx/conf
$ vim nginx.conf
...
http {
  ...
  include /usr/local/nginx/conf/vhost/*.conf;
}
stream {
  include /usr/local/nginx/conf/vhost/stream/*.conf;
}

$ mkdir vhost
$ vim vhost/nacos.conf
upstream nacos {
  server 192.168.1.11:8848;
  server 192.168.1.12:8848;
  server 192.168.1.13:8848;
}
server {
  listen 8800;
  location / {
    proxy_pass http://nacos;
  }
}

$ mkdir vhost/stream
$ vim vhost/stream/nacos.conf
upstream nacos {
  server 192.168.1.11:9848;
  server 192.168.1.12:9848;
  server 192.168.1.13:9848;
}
server {
  listen 9800;
  proxy_pass nacos;
}
```

# 5 客户端配置
bootstrap.yml
```
server:
  port: 8001

spring:
  application:
    name: nacos-client
  cloud:
    nacos:
      discovery:
        server-addr: 192.168.1.10:8800 # Nginx地址（此处Nginx主机ip为192.168.1.10），客户端会访问主端口（http，此处设为8800），以及偏移量1000的端口（TCP，此处将为9800）

management:
  endpoints:
    web:
      exposure:
        include:
        - nacos-discovery # Nacos Discovery提供的Endpoint
```