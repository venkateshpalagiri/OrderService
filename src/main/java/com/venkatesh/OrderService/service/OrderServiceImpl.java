package com.venkatesh.OrderService.service;

import com.venkatesh.OrderService.entity.Order;
import com.venkatesh.OrderService.model.OrderRequest;
import com.venkatesh.OrderService.repository.OrderRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Log4j2
public class OrderServiceImpl implements OrderService{
    @Autowired
    private OrderRepository orderRepository;
    @Override
    public long placeOrder(OrderRequest orderRequest) {
        //Order Entity -> Save the data with Status Order Created
        //Product Service -> Block Products (Reduce the Quantity)
        //Payment Service -> Payments -> Success -> COMPLETE,   Else CANCELLED
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>Placing Order Request: {}",orderRequest);
        Order order= Order.builder().amount(orderRequest.getTotalAmount())
                .orderDate(Instant.now()).orderStatus("CREATED")
                .quantity(orderRequest.getQuantity()).build();
        order=orderRepository.save(order);
        log.info(">>>>>>>>>>>>>>>>Order placed successfully: {}",order.getId());
        return order.getId() ;
    }
}
