package com.sample.springcloud.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sample.springcloud.dao.OrderDao;
import com.sample.springcloud.entity.Order;
import com.sample.springcloud.service.AccountService;
import com.sample.springcloud.service.OrderService;

import io.seata.core.context.RootContext;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private AccountService accountService;

    @Override
    public Order create(Long userId, String commodityCode, Integer count) {
        log.info("order service begin ... xid: " + RootContext.getXID());

        int money = calculateMoney(commodityCode, count);

        accountService.debit(userId, money);

        log.info("creating order: user_id={}, commodity_code={}, count={}, money={}", userId, commodityCode, count,
                money);
        Order order = new Order();
        order.setUserId(userId);
        order.setCommodityCode(commodityCode);
        order.setCount(count);
        order.setMoney(money);
        orderDao.create(order);

        log.info("order service end ...");
        return order;
    }

    private int calculateMoney(String commodityCode, Integer count) {
        return 100 * count;
    }

}
