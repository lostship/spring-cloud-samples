package com.sample.springcloud;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class BusinessServiceApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(BusinessServiceApplication.class, args);
        DataSource ds = context.getBean(DataSource.class);
        System.out.println(ds.getClass());

        SqlSessionFactory ssf = context.getBean(SqlSessionFactory.class);
        System.out.println(ssf.getConfiguration().getEnvironment().getDataSource().getClass());

    }

}
