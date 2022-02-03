package com.sample.springcloud.dao;

import org.apache.ibatis.annotations.Mapper;

import com.sample.springcloud.entity.Payment;

@Mapper
public interface PaymentDao {

    Payment getById(Long id);

    int create(Payment payment);

}
