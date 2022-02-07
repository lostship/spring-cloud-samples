package com.sample.springcloud.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sample.springcloud.dao.AccountDao;
import com.sample.springcloud.service.AccountService;

import io.seata.core.context.RootContext;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountDao accountDao;

    public void debit(Long userId, Integer money) {
        log.info("account service begin ... xid: " + RootContext.getXID());
        log.info("debiting account: user_id={}, money-{}", userId, money);
        accountDao.debit(userId, money);
        log.info("account service end ...");
    }

}
