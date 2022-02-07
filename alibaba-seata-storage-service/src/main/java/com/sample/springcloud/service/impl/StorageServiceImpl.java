package com.sample.springcloud.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sample.springcloud.dao.StorageDao;
import com.sample.springcloud.service.StorageService;

import io.seata.core.context.RootContext;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StorageServiceImpl implements StorageService {

    @Autowired
    private StorageDao storageDao;

    @Override
    public void deduct(String commodityCode, Integer count) {
        log.info("storage service begin ... xid: " + RootContext.getXID());
        log.info("deducting storage: commodity_code={}, count-{}", commodityCode, count);
        storageDao.deduct(commodityCode, count);
        log.info("storage service end ...");
    }

}
