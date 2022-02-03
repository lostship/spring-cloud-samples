package com.sample.springcloud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sample.springcloud.dao.PaymentDao;
import com.sample.springcloud.entity.Payment;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentDao paymentDao;

    @Override
    public Payment getById(Long id) {
        return paymentDao.getById(id);
    }

    @Override
    public int create(Payment payment) {
        return paymentDao.create(payment);
    }

}
