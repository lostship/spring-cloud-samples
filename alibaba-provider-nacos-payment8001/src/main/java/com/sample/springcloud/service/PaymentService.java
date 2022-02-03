package com.sample.springcloud.service;

import com.sample.springcloud.entity.Payment;

public interface PaymentService {

    Payment getById(Long id);

    int create(Payment payment);

}
