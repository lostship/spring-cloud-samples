package com.sample.springcloud.service;

import com.sample.springcloud.entity.Order;

public interface BusinessService {

    Order purchase(Long userId, String commodityCode, Integer count);

}
