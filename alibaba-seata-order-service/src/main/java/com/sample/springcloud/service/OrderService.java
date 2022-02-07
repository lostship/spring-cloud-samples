package com.sample.springcloud.service;

import com.sample.springcloud.entity.Order;

public interface OrderService {

    Order create(Long userId, String commodityCode, Integer count);

}
