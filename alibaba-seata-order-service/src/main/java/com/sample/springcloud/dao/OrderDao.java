package com.sample.springcloud.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

import com.sample.springcloud.entity.Order;

@Mapper
public interface OrderDao {

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into `order` (`user_id`, `commodity_code`, `count`, `money`) values (#{userId}, #{commodityCode}, #{count}, #{money})")
    int create(Order order);

}
