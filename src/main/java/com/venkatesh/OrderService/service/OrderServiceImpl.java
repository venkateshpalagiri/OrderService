package com.venkatesh.OrderService.service;

import com.venkatesh.OrderService.entity.Order;
import com.venkatesh.OrderService.model.OrderRequest;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService{
    @Override
    public long placeOrder(OrderRequest orderRequest) {
        return 0;
    }

    @Override
    public Order getOrder(long id) {
        return null;
    }
}
