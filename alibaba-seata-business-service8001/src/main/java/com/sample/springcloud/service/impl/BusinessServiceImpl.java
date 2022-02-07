package com.sample.springcloud.service.impl;

import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sample.springcloud.entity.CommonResult;
import com.sample.springcloud.entity.Order;
import com.sample.springcloud.service.BusinessService;
import com.sample.springcloud.service.OrderService;
import com.sample.springcloud.service.StorageService;

import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BusinessServiceImpl implements BusinessService {

    @Autowired
    private StorageService storageService;
    @Autowired
    private OrderService orderService;

    @Override
    @GlobalTransactional(name = "sample-purchase-tx", rollbackFor = Exception.class, timeoutMills = 30000)
    public Order purchase(Long userId, String commodityCode, Integer count) {
        log.info("purchase begin ... xid: " + RootContext.getXID());
        storageService.deduct(commodityCode, count);

        CommonResult<Order> orderResult = orderService.create(userId, commodityCode, count);

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (ThreadLocalRandom.current().nextBoolean()) {
            throw new RuntimeException("random exception mock!");
        }

        log.info("purchase end ...");
        return orderResult.getData();
    }

}
