package com.company.service;

import org.springframework.amqp.core.Message;

public interface ConsumerService {
    Object consumerMessage(long msg);
}
